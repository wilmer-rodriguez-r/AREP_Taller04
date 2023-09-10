package org.example.miniSpring.controllers;

import org.example.handlers.Request;
import org.example.miniSpring.annotations.Component;
import org.example.miniSpring.annotations.GetMapping;
import org.example.miniSpring.annotations.PostMapping;
import org.json.JSONObject;

import java.util.*;

/**
 * Controlador de ejemplo
 */
@Component
public class ExampleController {

    private static final Map<String, JSONObject> persistenceExample = new HashMap<>();

    /**
     * Método que se encarga del obtener una persona.
     * @param request Request que posee la solicitud realizada.
     * @return String con el nombre de la persona.
     */
    @GetMapping("/hello?{name}")
    public static String getPerson(Request request) {
        String param = request.getParam("name");
        if (persistenceExample.containsKey(param)) {
            JSONObject jsonObject = persistenceExample.get(param);
            return "Hola: " + jsonObject.get("name") +" "+ jsonObject.get("lastName");
        }
        return "La persona no existe";
    }

    /**
     * Método que guarda una persona.
     * @param request Request que posee la solicitud realizada.
     * @return String con el nombre de la nueva persona.
     */
    @PostMapping("/hello")
    public static String postPerson(Request request) {
        JSONObject body = request.getBody();
        persistenceExample.put(body.getString("name"), body);
        return "La persona: " + body.getString("name") + " fue añadida";
    }
}