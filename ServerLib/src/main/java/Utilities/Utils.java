package Utilities;
import Manager.Control;
import com.esotericsoftware.kryonet.Connection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hyperspherestudio.kryonet.FileCrypto;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

public class Utils {


    public static int convertToHash2(int val1, int val2) {
        return (999 + val1) * 999 + val2;
    }

    public static int convertToHash3(int val1, int val2, int val3) {
        return 37 * ((37 + val1) * 37 + val2) + val3;
    }

    public static boolean floatArrayIntersection(float[] array1, float[] array2) {
        return rectangleIntersection(array1[0], array1[1], array1[2], array1[3], array2[0], array2[1], array2[2], array2[3]);
    }

    public static boolean rectangleIntersection(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return Math.max(x1, x2) < Math.min(x1 + w1, x2 + w2) && Math.max(y1, y2) < Math.min(y1 + h1, y2 + h2);
    }

    public static boolean floatArrayIntersection(float[] list, float x, float y, float w, float h) {
        return rectangleIntersection(list[0], list[1], list[2], list[3], x, y, w, h);
    }

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            if(!path.contains(Control.root()))path = Control.root() + path;
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null)
                builder.append(line + "\n");

            br.close();
        } catch (IOException e) {
            Control.logError(e);
        }
        return builder.toString();
    }


    public static JsonNode readJson(String string){
        try{
            return new ObjectMapper().readTree(string);
        }catch (Exception e){
            Control.logError(e);
            return null;
        }
    }

    public static String getNext(StringTokenizer st, final String DELIM) {
        String value = st.nextToken();
        if (DELIM.equals(value))
            value = null;
        else if (st.hasMoreTokens())
            st.nextToken();
        return value;
    }

    public static int getLineAmt(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Control.root() + path));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }

    public static void writeToJson(String path, Map<String, Object> map) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(Control.root() + path));
            ObjectMapper mapper2 = new ObjectMapper();
            mapper2.enable(SerializationFeature.INDENT_OUTPUT);
            writer.write(mapper2.writeValueAsString(map));
            writer.close();
        } catch (IOException e) {
            Control.logError(e);
        }
    }


    public static void writeToJsonWithoutIndent(String path, Object map) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(Control.root() + path));
            ObjectMapper mapper2 = new ObjectMapper();
            writer.write(mapper2.writeValueAsString(map));
            writer.close();
        } catch (IOException e) {
            Control.logError(e);
        }
    }

    public static JsonNode readFromJson(String path) {
        try {
            return new ObjectMapper().readTree(new File(Control.root() + path));
        } catch (IOException e) {
           Control.logError(e);
        }
        return null;
    }

    public static JsonNode readFromJson(File file) {
        try {
            return new ObjectMapper().readTree(file);
        } catch (IOException e) {
            Control.logError(e);
        }
        return null;
    }

    public static void writeToTXT(String path, String data) {
        try {
            File file = new File(Control.root() + path);
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(data);
            br.newLine();
            br.close();
            fr.close();
        } catch (IOException e) {
            Control.logError(e);
        }
    }

    public static String[] tokenize(String string, char delimiter) {
        String[] temp = new String[(string.length() / 2) + 1];
        int wordCount = 0;
        int i = 0;
        int j = string.indexOf(delimiter);

        while (j >= 0) {
            temp[wordCount++] = string.substring(i, j);
            i = j + 1;
            j = string.indexOf(delimiter, i);
        }

        temp[wordCount++] = string.substring(i);
        String[] result = new String[wordCount];
        System.arraycopy(temp, 0, result, 0, wordCount);
        return result;
    }

    public static void removeLineFromTxt(String in, String linetoRemove) throws IOException {
        File inputFile = new File(in);
        File tempFile = new File(in + "skjsdf");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(linetoRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        tempFile.renameTo(inputFile);
    }

    public static ArrayList<String> readLines(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(Control.root() + path))) {
            String line;
            ArrayList<String> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            Control.logError(e);
            return null;
        }
    }

    public static void serverEventLog(String string) {
        writeToTXT("ServerEvent.txt", string);
    }

    public static void sendEmergencyMessage(String message) {

    }

}
