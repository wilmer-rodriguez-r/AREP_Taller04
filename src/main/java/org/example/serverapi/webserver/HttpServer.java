package org.example.serverapi.webserver;

import org.example.serverapi.minispark.MiniSpark;
import org.example.serverapi.files.exception.ExceptionFile;
import org.example.serverapi.files.filesFactory.FileFactoryImpl;
import org.example.serverapi.files.filesFactory.FileFactoryInterface;

import java.io.*;
import java.net.*;

/***
 * Clase que actua de servidor HTTP
 */
public class HttpServer {

    private static FileFactoryInterface filesFactory;
    private static final String[] resources = {"/index.html", "/app.js", "/apiclient.js", "/cat.jpg", "/script.js",
            "/favicon.ico", "/fondo.jpg", "/NotFound.html", "/style.css"};

    public static void load() {
        filesFactory = new FileFactoryImpl();
        for(String resource: resources) {
            MiniSpark.get(resource, (request, response) -> {
                try {
                    return filesFactory.getInstance(request.getResource()).readFile(URI.create(MiniSpark.path + request.getResource()));
                } catch (IOException e) {
                    throw new ExceptionFile(ExceptionFile.NOT_FOUND);
                }
            });
        }
    }
}