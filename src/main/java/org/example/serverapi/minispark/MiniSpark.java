package org.example.serverapi.minispark;

import org.example.serverapi.files.File;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MiniSpark extends Thread {

    private final int port;
    static Map<String, ServiceControllerInterface> servicesGet = new HashMap<>();
    static Map<String, ServiceControllerInterface> servicePost =  new HashMap<>();
    public static final String path = "./target/classes/public";

    public MiniSpark(int port) {
        this.port = port;
    }

    public static void get(String query, ServiceControllerInterface service) {
        servicesGet.put(query, service);
    }

    public static void post(String query, ServiceControllerInterface service) {
        servicePost.put(query, service);
    }

    public static ServiceControllerInterface search(String query) {
        return servicesGet.get(query);
    }

    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    System.out.println("Listening ...");
                    Socket clientSocket = serverSocket.accept();
                    MiniSparkThread threadRequest = new MiniSparkThread(clientSocket);
                    threadRequest.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + port);
        }
    }

}
