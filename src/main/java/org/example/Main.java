package org.example;


import org.example.controller.MovieController;
import org.example.persistence.MoviePersistence;

public class Main {
    public static void main(String[] args) {
        MovieController.getInstance();
        SocketServer.startSocket(35000);
    }
}