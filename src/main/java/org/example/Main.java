package org.example;

import org.example.miniSpring.LoadComponents;
import org.example.miniSpring.MiniSpringServer;
import org.example.miniSpring.annotations.Component;
import org.example.miniSpring.annotations.GetMapping;
import org.example.movies.SocketServer;
import org.example.movies.controller.MovieController;
import org.example.miniSpark.webserver.HttpServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/***
 * Clase principal que se encarga de desplegar el servidor http y el backend
 */
public class Main {

    public static Map<String, Method> servicios = new HashMap<>();

    /***
     * Función que ejecuta el servidor http y el backend en sus respectivos puertos.
     * @param args un String[] donde puede recibir parámetros
     */
    public static void main(String[] args) throws ClassNotFoundException {
        List<Class<?>> classes = LoadComponents.getClasses(args[0]);
        LoadComponents.loadAnnotations(classes);
        loadServers();
    }

    public static void loadServers() {
        MovieController.getInstance();
        MiniSpringServer springServer = new MiniSpringServer(35001);
        SocketServer backServer = new SocketServer(35000);
        backServer.start();
        springServer.start();
    }

}