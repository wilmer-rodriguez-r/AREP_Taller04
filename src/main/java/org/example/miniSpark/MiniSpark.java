package org.example.miniSpark;

import org.example.files.File;
import org.example.files.exception.ExceptionFile;
import org.example.files.filesFactory.FileFactoryImpl;
import org.example.files.filesFactory.FileFactoryInterface;
import org.json.JSONObject;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.*;

/**
 * Clase del Servidor Spark
 */
public class MiniSpark extends Thread{

    /**
     * Atributo que contiene los servicios get registrados
     */
    static Map<String, ServiceControllerInterface> servicesGet = new HashMap<>();

    /**
     * Atributo que contiene los servicios post registrados
     */
    static Map<String, ServiceControllerInterface> servicesPost =  new HashMap<>();

    /**
     * Atributo que contiene el path de la carpeta para los archivos.
     */
    public static final String path = "./target/classes/public";

    private final int port;

    /**
     * Constructor de la clase MiniSpark
     * @param port el puerto por donde iniciara el server
     */
    public MiniSpark(int port) {
        this.port = port;
    }

    /**
     * Método que se encarga de registrar los endpoints y las funciones lamba a ejecutar posteriormente.
     * @param query String que es el endpoint a registrar.
     * @param service ServiceControllerInterface será la función lamba a ejecutar.
     */
    public static void get(String query, ServiceControllerInterface service) {
        servicesGet.put(query, service);
    }

    /**
     * Método que se encarga de registrar los endpoints y las funciones lamba a ejecutar posteriormente.
     * @param query String que es el endpoint a registrar.
     * @param service ServiceControllerInterface será la función lamba a ejecutar.
     */
    public static void post(String query, ServiceControllerInterface service) {
        servicesPost.put(query, service);
    }

    /**
     * Método que se encarga de buscar la función lambda a ejecutar.
     * @param query String endpoint relacionado con la función.
     * @param verb String el verbo HTTP.
     * @return ServiceControllerInterface que contiene la función lambda.
     */
    public static ServiceControllerInterface search(String query, String verb) {
        if(verb.equals("GET")) {
            return servicesGet.get(query);
        }
        return servicesPost.get(query);
    }

    @Override
    public void run() {
        exampleMiniSpark();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Listening ...");
                Socket clientSocket = serverSocket.accept();
                MiniSparkThread miniSparkThread = new MiniSparkThread(clientSocket);
                miniSparkThread.start();
            }
        } catch (Exception e) {
            System.out.println("Could not listen on port: " + port);
        }
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
            //JSONObject personJson = request.getBody();
            //String name = (String) personJson.get("nombre");
            //persons.put(name, personJson);
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
