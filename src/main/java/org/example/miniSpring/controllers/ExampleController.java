package org.example.miniSpring.controllers;

import org.example.handlers.Request;
import org.example.miniSpring.annotations.Component;
import org.example.miniSpring.annotations.GetMapping;
import org.example.miniSpring.annotations.PostMapping;
import java.util.*;

/**
 * Controlador de ejemplo
 */
@Component
public class ExampleController {

    private static final Map<String, String> persistenceExample = new HashMap<>();

    /**
     * Método que se encarga del obtener una persona.
     * @param request Request que posee la solicitud realizada.
     * @return String con el nombre de la persona.
     */
    @GetMapping("/hello?{name}")
    public static String getPerson(Request request) {
        String param = request.getParam("name");
        if (persistenceExample.containsKey(param))
            return "Hola: " + param;
        return "La persona no existe";
    }

    /**
     * Método que guarda una persona.
     * @param request Request que posee la solicitud realizada.
     * @return String con el nombre de la nueva persona.
     */
    @PostMapping("/hello")
    public static String postPerson(Request request) {
        String body = request.getBody();
        String key = body.split("\"")[3];
        persistenceExample.put(key, request.getBody());
        return "La persona: " + key + " fue añadida";
    }
}