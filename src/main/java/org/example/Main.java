package org.example;

import org.example.movies.SocketServer;
import org.example.movies.controller.MovieController;
import org.example.serverapi.files.FileText;
import org.example.serverapi.files.exception.ExceptionFile;
import org.example.serverapi.files.filesFactory.FileFactoryImpl;
import org.example.serverapi.minispark.MiniSpark;
import org.example.serverapi.minispark.handlers.Request;
import org.example.serverapi.minispark.handlers.Response;
import org.example.serverapi.webserver.HttpServer;

import java.io.IOException;
import java.net.URI;

/***
 * Clase principal que se encarga de desplegar el servidor http y el backend
 */
public class Main {

    /***
     * Función que ejecuta el servidor http y el backend en sus respectivos puertos.
     * @param args un String[] donde puede recibir parámetros
     */
    public static void main(String[] args) throws IOException, ExceptionFile {
        HttpServer.load();
        MovieController.getInstance();
        MiniSpark miniSpark = new MiniSpark(5500);
        SocketServer backServer =  new SocketServer(35000);
        miniSpark.start();
        backServer.start();

        MiniSpark.get("/hola?{name}", (request, response) -> "hola: " + request.getParam("name"));
    }

}