package org.example;


import org.example.backend.SocketServer;
import org.example.backend.controller.MovieController;
import org.example.frontend.HttpServer;

/***
 * Clase principal que se encarga de desplegar el servidor http y el backend
 */
public class Main {

    /***
     * Función que ejecuta el servidor http y el backend en sus repectivos puertos.
     * @param args un String[] donde puede recibir parametros
     */
    public static void main(String[] args) {
        MovieController.getInstance();
        SocketServer socketServer = new SocketServer(35000);
        socketServer.start();
        HttpServer httpServer = new HttpServer(5500);
        httpServer.start();
        System.out.println("En ejecución...");
    }
}