package org.example;

import org.example.files.FileReader;
import org.example.files.FileReaderFileImage;
import org.example.files.FileReaderFileText;
import org.example.files.filesFactory.FilesFactoryImplementation;
import org.example.files.filesFactory.FilesFactoryInterface;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Clase que actua de servidor HTTP
 */
public class HttpServer{

    private final FilesFactoryInterface factoryFiles;
    private final String path = "./target/classes/public/";
    private final int port;

    /***
     * Constructor de la clase HttpServer
     * @param port un int que corresponde al puerto donde correra
     */
    public HttpServer(int port) {
        this.port = port;
        factoryFiles = new FilesFactoryImplementation();
    }

    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    System.out.println("Listening ...");
                    Socket clientSocket = serverSocket.accept();
                    URI uri = getResource(clientSocket);
                    System.out.println(uri.getPath());
                    readFile(uri, clientSocket);
                    clientSocket.close();
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: 5500.");
        }
    }

    public URI getResource(Socket clientSocket) throws IOException, URISyntaxException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine = in.readLine();
        System.out.println("Received: " + inputLine);
        Pattern pattern_text = Pattern.compile("[0-9a-zA-Z]*\\.(html|jpg|png|js|css|ico|gif)");
        Matcher matcher = pattern_text.matcher(inputLine);
        if (matcher.find()) {
            return new URI(path + matcher.group());
        }
        throw new IOException();
    }

    public void readFile(URI path, Socket clientSocket) throws Exception {
        String resource = path.getPath();
        FileReader fileReader = factoryFiles.getInstance(resource);
        fileReader.readFile(path, clientSocket);
    }
}