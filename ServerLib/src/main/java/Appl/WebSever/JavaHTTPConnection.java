package Appl.WebSever;

import Manager.Control;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class JavaHTTPConnection implements Runnable {


    private final Socket connect;
    public static byte[] VerifiedMessage, ErrorMessage, WrongLink, ActionNotAvailable;

    public JavaHTTPConnection(Socket socket) {
        this.connect = socket;
        new Thread(this).start();
        System.out.println("Received WebServer Connection:" + socket.getInetAddress());
    }

    public static void initialize() {
        File WEB_ROOT = new File(Control.root());
        VerifiedMessage = initializeFile(WEB_ROOT, Control.serverSettings.VerifiedWebPagePath());
        ErrorMessage = initializeFile(WEB_ROOT, Control.serverSettings.ErrorMessageWebPagePath());
        WrongLink = initializeFile(WEB_ROOT, Control.serverSettings.WrongLinkWebPagePath());
        ActionNotAvailable = initializeFile(WEB_ROOT, Control.serverSettings.ActionNotAvailableWebPagePath());
    }

    public static byte[] initializeFile(File webroot, String path) {
        File file = new File(webroot, path);
        int fileLength = (int) file.length();
        try {
            return readFileData(file, fileLength);
        } catch (IOException e) {
            Control.logError(e);
            return null;
        }
    }


    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        try {
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            out = new PrintWriter(connect.getOutputStream());
            dataOut = new BufferedOutputStream(connect.getOutputStream());
            String string = in.readLine();
            readLink(URLManager.ReadURL(string), out, dataOut);
        } catch (Exception i) {
            i.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                dataOut.close();
                connect.close();
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }
        }
    }


    public static void readLink(HyperURL hyperURL, PrintWriter out, OutputStream dataOut) throws IOException {
        if (hyperURL != null) {
            if (hyperURL.Type.equals(URLManager.Type_Player)) {
                boolean found = false;
                for (URLAction urlAction : URLAction.hashMap.values()) {
                    if (urlAction.type.equals(hyperURL.Action)) {
                        URLManager urlManager = urlAction.getURLManager(hyperURL);
                        if (urlManager != null && urlManager.isValid(hyperURL)) {
                            urlManager.removeLink(hyperURL.Action);
                            displayData(VerifiedMessage, out, dataOut);
                        } else displayData(ActionNotAvailable, out, dataOut);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    displayData(WrongLink, out, dataOut);
                }
            }
        }
    }

    public static void displayData(byte[] array, PrintWriter out, OutputStream dataOut) throws IOException {
        out.println("HTTP/1.1 200 OK ");
        out.println("Server: Java HTTP Server from " + Control.serverSettings.ServerName() + " : 1.0");
        out.println("Date: " + new Date());
        out.println("Content-type: text/html");
        out.println("Content-length: " + array.length);
        out.println();
        out.flush();
        dataOut.write(array, 0, array.length);
        dataOut.flush();
    }

    private static byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }
}
