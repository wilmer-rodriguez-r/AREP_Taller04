package org.example;


import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

    public static void startSocket(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    System.out.println("Listening ...");
                    new ClientSocket(serverSocket.accept()).start();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: 35000.");
        }
    }


}
