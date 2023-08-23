package org.example;


import org.example.backend.SocketServer;
import org.example.backend.controller.MovieController;
import org.example.frontend.HttpServer;

public class Main {
    public static void main(String[] args) {
        MovieController.getInstance();
        SocketServer socketServer = new SocketServer(35000);
        socketServer.start();
        HttpServer httpServer = new HttpServer();
        httpServer.start();
        System.out.println("En ejecución...");
    }
}