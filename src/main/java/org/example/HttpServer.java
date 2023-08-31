package org.example;

import org.example.MiniSpark;
import org.example.files.FileReader;
import org.example.files.exception.ExceptionFile;
import org.example.files.filesFactory.*;
import java.io.*;
import java.net.*;
import java.util.regex.*;

/***
 * Clase que actua de servidor HTTP
 */
public class HttpServer{

    private final FilesFactoryInterface factoryFiles;
    private final int port;

    private String resource;

    /***
     * Constructor de la clase HttpServer
     * @param port un int que corresponde al puerto donde correra
     */
    public HttpServer(int port) {
        this.port = port;
        factoryFiles = new FilesFactoryImplementation();
    }

    /***
     * Inicia el servidor web y empieza a escuchar por el puerto
     */
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    System.out.println("Listening ...");
                    Socket clientSocket = serverSocket.accept();
                    try {
                        getResource(clientSocket);
                        readFile();
                        clientSocket.close();
                    } catch (ExceptionFile e) {
                        badRequest(clientSocket);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + port);
        }
    }

    /***
     * Obtiene el recurso solicitado a partir de la petición
     * @param clientSocket (Socket) Es el socket donde el cliente envío la petición.
     * @return (URI) el path del recurso que se solicita.
     * @throws IOException Cuando se construye mal la salida o entrada.
     * @throws ExceptionFile Cuando el archivo solicitado no se encuentra
     */
    public void getResource(Socket clientSocket) throws IOException, ExceptionFile {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine = in.readLine();
        System.out.println("Received: " + inputLine);
        Pattern pattern_text = Pattern.compile("/.*\\.(html|jpg|png|js|css|ico|gif)");
        Matcher matcher = pattern_text.matcher(inputLine);
        if (matcher.find()) {
            String path = "./target/classes/public";
            resource = matcher.group();
            System.out.println(resource);
            MiniSpark.get(resource, (str -> {
                try {
                    factoryFiles.getInstance(str).readFile(URI.create(path + str), clientSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }

    /**
     * Lee el archivo solicitado y lo envía al cliente
     * @throws ExceptionFile Cuando no se encuentre el recurso
     */
    public void readFile() throws ExceptionFile, IOException {
        MiniSpark.search(resource).send(resource);
    }

    /***
     * Informa al cliente que la petición del recurso solicitado no se encontró.
     * @param clientSocket (Socket) el socket donde se le envía la respuesta al cliente.
     * @throws IOException en caso de que no se pudo leer el archivo solicitado.
     */
    private void badRequest(Socket clientSocket) throws IOException {
        FileReader.badRequest(clientSocket);
        clientSocket.close();
    }
}