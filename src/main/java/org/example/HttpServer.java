package org.example;

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
                        URI uri = getResource(clientSocket);
                        System.out.println(uri.getPath());
                        readFile(uri, clientSocket);
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
     * @throws URISyntaxException Cuando la Uri no está bien construida
     * @throws ExceptionFile Cuando el archivo solicitado no se encuentra
     */
    public URI getResource(Socket clientSocket) throws IOException, URISyntaxException, ExceptionFile {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine = in.readLine();
        System.out.println("Received: " + inputLine);
        Pattern pattern_text = Pattern.compile("[0-9a-zA-Z]*\\.(html|jpg|png|js|css|ico|gif)");
        Matcher matcher = pattern_text.matcher(inputLine);
        if (matcher.find()) {
            String path = "./target/classes/public/";
            return new URI(path + matcher.group());
        }
        throw new ExceptionFile(ExceptionFile.NOT_FOUND);
    }

    /**
     * Lee el archivo solicitado y lo envía al cliente
     * @param path (URI) la dirección donde se encuentra el recurso que se solicitó
     * @param clientSocket (Socket) es el socket donde se envía los recursos para el cliente
     * @throws ExceptionFile Cuando no se encuentre el recurso
     */
    public void readFile(URI path, Socket clientSocket) throws ExceptionFile, IOException {
        String resource = path.getPath();
        FileReader fileReader = factoryFiles.getInstance(resource);
        fileReader.readFile(path, clientSocket);
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