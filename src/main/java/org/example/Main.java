package org.example;

import org.example.movies.SocketServer;
import org.example.movies.controller.MovieController;
import org.example.serverapi.files.File;
import org.example.serverapi.files.exception.ExceptionFile;
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

    /***
     * Clase que se encarga de exponer endpoints de ejemplo
     */
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
            try {
                //Creamos una instancia que leerá nuestro archivo
                FileFactoryInterface fileFactory = new FileFactoryImpl();
                //Leemos el archivo dándole el nombre del recurso y la ubicación de este
                File file = fileFactory.getInstance("/cat.jpg");
                URI pathFile = URI.create(MiniSpark.path + "/cat.jpg");
                return file.readFile(pathFile);
            } catch (Exception e) {
                throw new ExceptionFile(ExceptionFile.NOT_FOUND);
            }
        });
    }

}