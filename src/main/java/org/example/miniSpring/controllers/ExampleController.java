package org.example.miniSpring.controllers;

import org.example.handlers.Request;
import org.example.miniSpring.annotations.Component;
import org.example.miniSpring.annotations.GetMapping;
import org.example.miniSpring.annotations.PostMapping;
import java.util.*;

@Component
public class ExampleController {

    private static final Map<String, String> persistenceExample = new HashMap<>();

    @GetMapping("/hello?{name}")
    public static String getPerson(Request request) {
        String param = request.getParam("name");
        if (persistenceExample.containsKey(param))
            return "Hola: " + param;
        return "La persona no existe";
    }

    @PostMapping("/hello")
    public static String postPerson(Request request) {
        String body = request.getBody();
        String key = body.split("\"")[3];
        persistenceExample.put(key, request.getBody());
        return "La persona: " + key + " fue añadida";
    }
}
