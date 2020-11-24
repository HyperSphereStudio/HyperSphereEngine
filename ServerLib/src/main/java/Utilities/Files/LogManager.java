package Utilities.Files;
import Manager.Control;
import Utilities.TimeKeeper;
import Utilities.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

public class LogManager {

    private static ArrayList<LogManager> logManagers = new ArrayList<>();
    private ArrayList<HashSet<String>> set = new ArrayList<>();
    private BufferedWriter bufferedWriter;
    private boolean hashSet, closed, appendTime;
    private String path, name;
    private int daysInThePastValid, currentDay;
    private static final String extension = ".hyperlog";

    public LogManager(String name, String path, int daysInThePastValid, boolean hashSet, boolean appendTime) {
        this.name = name;
        this.daysInThePastValid = daysInThePastValid;
        if (!path.endsWith("/"))
            path += "/";
        this.path = path;
        this.appendTime = appendTime;
        this.hashSet = hashSet;
        this.currentDay = TimeKeeper.currentDay;
        logManagers.add(this);
    }


    public LogManager(String name, String path, int daysInThePastValid, boolean appendTime) {
        this(name, path, daysInThePastValid, false, appendTime);
    }

    public void initialize(){
        File file = new File(Control.root() + path);
        if(!file.exists())file.mkdir();
        if (hashSet) {
            for (int i = currentDay; i >= currentDay - daysInThePastValid; --i) {
                if (new File(Control.root() + path + i + extension).exists()) {
                    try {
                        set.add((HashSet<String>) new ObjectMapper().treeToValue(Utils.readFromJson(path + i + extension), HashSet.class));
                    } catch (JsonProcessingException e) {
                        Control.logError(e);
                    }
                }
            }
        }else {
            try {
                newStream();
            } catch (IOException e) {
                Control.logError(e);
            }
        }
        checkForOldFiles();
        System.out.println("Initialized Log:" + Control.root() + path + currentDay + extension);
    }

    public void newStream() throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(new File(Control.root() + path + currentDay + extension), true));
    }

    private void checkForOldFiles() {
        for (File file : new File(Control.root() + path).listFiles()) {
            try {
                if (file.getName().endsWith(extension)) {
                    if (TimeKeeper.currentDay - Integer.parseInt(file.getName().substring(0, file.getName().length() - extension.length())) > daysInThePastValid) {
                        file.delete();
                    }
                }
            } catch (NumberFormatException e) {
                Control.logError(e);
            }
        }
    }

    public void update() {
        try {
            if (TimeKeeper.currentDay != currentDay) {
                if (hashSet) {
                    saveHashSet();
                } else {
                    bufferedWriter.close();
                    newStream();
                }
                currentDay = TimeKeeper.currentDay;
                checkForOldFiles();
            } else {
                if(hashSet)
                saveHashSet();
                else bufferedWriter.flush();
            }

        } catch (Exception e) {
            Control.logError(e);
        }
    }

    public boolean contains(String string) {
        if (!hashSet) throw new RuntimeException("Not HashSet Mode");
        for (HashSet<String> hashSet : set) {
            if (hashSet.contains(string)) return true;
        }
        return false;
    }

    public static void initializeLogs(){
        for(LogManager logManager : logManagers){
            logManager.initialize();
        }
    }

    public static void writeLogs() {
        for (LogManager logManager : logManagers) {
            logManager.update();
        }
        System.out.println("Saved " + logManagers.size() + " Logs!");
    }

    public void log(String event) {
        if (appendTime) {
            event = TimeKeeper.timeForLog() + "   " + event;
        }
        if (!closed) {
            if (hashSet) {
                if (set.size() == 0) {
                    set.add(new HashSet<>());
                }
                set.get(0).add(event);
            } else {
                try {
                    bufferedWriter.write((event + "\n"));

                } catch (IOException e) {
                    Control.logError(e);
                }
            }
        } else {
            throw new RuntimeException("Log Is Closed");
        }
    }

    public void close() {
        if (!closed) {
            closed = true;
            if (hashSet) {
                saveHashSet();
            } else {
                try {
                   bufferedWriter.close();
                } catch (IOException e) {
                    Control.logError(e);
                }
            }
        }
    }

    public static void closeAll() {
        for (LogManager logManager : logManagers) {
            logManager.close();
        }
    }


    public void saveHashSet() {
        for (int i = currentDay; i > currentDay - daysInThePastValid && currentDay - i < set.size(); --i) {
            Utils.writeToJsonWithoutIndent(path + i + extension, set.get(currentDay - i));
        }
    }

    public static String logManagerNames() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (LogManager logManager : logManagers) {
            if (!logManager.hashSet) {
                if (stringBuilder.length() != 1) stringBuilder.append(", ");
                stringBuilder.append(logManager.name);
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String grep(String name, int date, int size, String regex, boolean endTobeg) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(name + " Log " + TimeKeeper.getCurrentDate());
        int counter = 0;
        for (LogManager logManager : logManagers) {
            if (logManager.name.equals(name) && !logManager.hashSet) {
                logManager.bufferedWriter.flush();
                if (date > logManager.daysInThePastValid)
                    throw new RuntimeException("Date Out of Range! Storage Only Kept For " + logManager.daysInThePastValid + " In The Past");
                String string;
                BiDirectionalLineInputStream biDirectionalLineInputStream = new BiDirectionalLineInputStream(Control.root() + logManager.path + (logManager.currentDay - date) + extension, endTobeg);
                Pattern pattern = Pattern.compile(regex.trim());

                while (biDirectionalLineInputStream.hasMore()) {
                    string = biDirectionalLineInputStream.readLine();
                    if(string == null)break;
                    string = string.trim();
                    if (!string.equals("")) {
                        if (pattern.matcher(string).find()) {
                            stringBuilder.append("\n");
                            stringBuilder.append(string);
                            if (counter++ == size) break;
                        }
                    }
                }

                biDirectionalLineInputStream.close();

                break;
            }
        }
        return stringBuilder.toString();
    }


}
