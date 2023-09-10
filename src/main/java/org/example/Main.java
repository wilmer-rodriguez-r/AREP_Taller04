package org.example;

import org.example.miniSpring.LoadComponents;
import org.example.miniSpring.MiniSpringServer;
import org.example.movies.SocketServer;
import org.example.movies.controller.MovieController;
import java.util.*;

/***
 * Clase principal que se encarga de desplegar el servidor http y el backend
 */
public class Main {

    /***
     * Función que ejecuta el servidor http y el backend en sus respectivos puertos.
     * @param args un String[] donde puede recibir parámetros
     * @throws ClassNotFoundException cuando la clase no se encuentra.
     */
    public static void main(String[] args) throws ClassNotFoundException {
        List<Class<?>> classes = LoadComponents.getClasses();
        LoadComponents.loadAnnotations(classes);
        loadServers();
    }

    /**
     * Sube los servers correspondientes.
     */
    public static void loadServers() {
        MovieController.getInstance();
        MiniSpringServer springServer = new MiniSpringServer(5500);
        SocketServer backServer = new SocketServer(35000);
        backServer.start();
        springServer.start();
    }

}