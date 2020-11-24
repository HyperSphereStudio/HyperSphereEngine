package Appl.WebSever;

import Manager.Control;

import java.io.IOException;
import java.net.ServerSocket;

public class JavaHTTPServer {



    public static void start() {
        try {
            ServerSocket serverConnect = new ServerSocket(Control.serverSettings.WebServerPort());
            System.out.println("Web Server Started");
            new Thread(() -> {
                while (true) {
                    try {
                        new JavaHTTPConnection(serverConnect.accept());
                    } catch (IOException e) {
                        Control.logError(e);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Control.logError(e);
                    }
                }
            }).start();

        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }





}