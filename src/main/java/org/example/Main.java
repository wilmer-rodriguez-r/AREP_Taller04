package org.example;

import org.example.movies.SocketServer;
import org.example.movies.controller.MovieController;
import org.example.serverapi.files.filesFactory.FileFactoryImpl;
import org.example.serverapi.files.filesFactory.FileFactoryInterface;
import org.example.serverapi.minispark.MiniSpark;
import org.example.serverapi.webserver.HttpServer;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/***
 * Clase principal que se encarga de desplegar el servidor http y el backend
 */
public class Main {

    /***
     * Función que ejecuta el servidor http y el backend en sus respectivos puertos.
     * @param args un String[] donde puede recibir parámetros
     */
    public static void main(String[] args) {
        HttpServer.load();
        MovieController.getInstance();
        SocketServer backServer = new SocketServer(35000);
        MiniSpark miniSparkServer = new MiniSpark(5500);
        backServer.start();
        miniSparkServer.start();
        exampleMiniSpark();
    }

    public static void exampleMiniSpark() {
        //persistence
        Map<String, JSONObject> persons = new LinkedHashMap<>();
        JSONObject dataPerson = new JSONObject();
        dataPerson.put("nombre", "wilmer");
        dataPerson.put("apellido", "rodriguez");
        persons.put("wilmer", dataPerson);
        //get
        MiniSpark.get("/persons?{name}", (request, response) -> {
            String name = request.getParam("name");
            return persons.get(name);
        });
        //post
        MiniSpark.post("/persons", (request, response) -> {
            JSONObject personJson = request.getBody();
            String name = (String) personJson.get("nombre");
            persons.put(name, personJson);
            return "The object has just been created";
        });
        //get Obtener archivos de manera local
        MiniSpark.get("/gato", (request, response) -> {
            //Creamos una instancia que leerá nuestro archivo
            FileFactoryInterface fileFactory = new FileFactoryImpl();
            //
            return fileFactory.getInstance("/cat.jpg").readFile(URI.create(MiniSpark.path + "/cat.jpg"));
        });
    }

}