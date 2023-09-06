package org.example.serverapi.minispark;

import org.example.serverapi.files.File;
import org.example.serverapi.files.exception.ExceptionFile;
import org.example.serverapi.minispark.handlers.Request;
import org.example.serverapi.minispark.handlers.Response;
import org.example.serverapi.webserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MiniSpark extends Thread{

    static Map<String, ServiceControllerInterface> servicesGet = new HashMap<>();
    static Map<String, ServiceControllerInterface> servicesPost =  new HashMap<>();
    private final int port;
    public static final String path = "./target/classes/public";


    public MiniSpark(int port) {
        this.port = port;
    }
    public static void get(String query, ServiceControllerInterface service) {
        servicesGet.put(query, service);
    }

    public static void post(String query, ServiceControllerInterface service) {
        servicesPost.put(query, service);
    }

    public static ServiceControllerInterface search(String query, String verb) {
        if(verb.equals("GET")) {
            return servicesGet.get(query);
        }
        return servicesPost.get(query);
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Listening ...");
                Socket clientSocket = serverSocket.accept();
                MiniSparkThread miniSparkThread = new MiniSparkThread(clientSocket);
                miniSparkThread.start();
            }
        } catch (Exception e) {
            System.out.println("Could not listen on port: " + port);
        }
    }

}
