package org.example.serverapi.webserver;

import org.example.serverapi.minispark.MiniSpark;
import org.example.serverapi.files.exception.ExceptionFile;
import org.example.serverapi.files.filesFactory.FileFactoryImpl;
import org.example.serverapi.files.filesFactory.FileFactoryInterface;
import org.example.serverapi.minispark.handlers.Request;
import org.example.serverapi.minispark.handlers.Response;

import java.io.*;
import java.net.*;
import java.util.Objects;

/***
 * Clase que actúa de servidor HTTP
 */
public class HttpServer {

    /***
     * Atributo que guarda la instancia de la fábrica.
     */
    private static FileFactoryInterface filesFactory;

    /***
     * Atributo que guarda los nombres de los recursos a solicitar en la página Web.
     */
    private static final String[] resources = {"/index.html", "/app.js", "/apiclient.js", "/cat.jpg", "/script.js",
            "/favicon.ico", "/fondo.jpg", "/NotFound.html", "/style.css"};

    /***
     * Carga los archivos necesarios para la página web.
     */
    public static void load() {
        filesFactory = new FileFactoryImpl();
        for(String resource: resources) {
            MiniSpark.get(resource, (request, response) -> {
                try {
                    return filesFactory.getInstance(request.getQuery()).readFile(URI.create(MiniSpark.path + request.getQuery()));
                } catch (IOException e) {
                    throw new ExceptionFile(ExceptionFile.NOT_FOUND);
                }
            });
        }
    }

}