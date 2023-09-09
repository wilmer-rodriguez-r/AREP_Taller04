package org.example.miniSpring.controllers;

import org.example.files.exception.ExceptionFile;
import org.example.files.filesFactory.FileFactoryImpl;
import org.example.files.filesFactory.FileFactoryInterface;
import org.example.handlers.Request;
import org.example.miniSpring.MiniSpringServer;
import org.example.miniSpring.annotations.Component;
import org.example.miniSpring.annotations.GetMapping;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;

@Component
public class HttpServerController {

    @GetMapping("/file")
    public static String getIndex(Request request) throws ExceptionFile, IOException {
        FileFactoryInterface filesFactory = new FileFactoryImpl();
        String endpoint = request.getEndpoint();
        byte[] bytesFile = filesFactory.getInstance(endpoint).readFile(URI.create(MiniSpringServer.path + endpoint));
        return Base64.getEncoder().encodeToString(bytesFile);
    }
}
