package org.example.miniSpark.webserver;

import org.example.miniSpark.MiniSpark;
import org.example.files.exception.ExceptionFile;
import org.example.files.filesFactory.FileFactoryImpl;
import org.example.files.filesFactory.FileFactoryInterface;

import java.io.*;
import java.net.*;

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
                    return filesFactory.getInstance(request.getEndpoint()).readFile(URI.create(MiniSpark.path + request.getEndpoint()));
                } catch (IOException e) {
                    throw new ExceptionFile(ExceptionFile.NOT_FOUND);
                }
            });
        }
    }

}