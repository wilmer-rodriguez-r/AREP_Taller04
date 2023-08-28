package org.example;

import org.example.files.FileReader;
import org.example.files.FileReaderFileImage;
import org.example.files.FileReaderFileText;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.Objects;

/***
 * Clase que actua de servidor HTTP
 */
public class HttpServer{

    private final String path = "./target/classes/public/";
    private final int port;

    /***
     * Constructor de la clase HttpServer
     * @param port un int que corresponde al puerto donde correra
     */
    public HttpServer(int port) {
        this.port = port;
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
                    if(!uri.getPath().contains(".ico")) {
                        readFile(uri, clientSocket);
                        clientSocket.close();
                    }
                } catch (IOException | URISyntaxException e) {
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                    out.write("HTTP/1.1 404 Not Found \r\n" +
                            "Content-Type: text/html \r\n" +
                            "\r\n");
                    out.close();
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: 35000.");
        }
    }

    public URI getResource(Socket clientSocket) throws IOException, URISyntaxException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine = in.readLine(), resource = "";
            System.out.println("Received: " + inputLine);
            if (inputLine.contains("GET") && !inputLine.contains(".ico")) {
                resource = inputLine.replaceAll("[/=?]", "").split(" ")[1];
            }
        if (Objects.equals(resource, "")) {
            return new URI(path + "index.html");
        }
        return new URI(path + resource);
    }

    public void readFile(URI path, Socket clientSocket) throws IOException {
        String resource = path.getPath();
        FileReader fileReader = null;
        if (resource.contains("html")) {
            fileReader = new FileReaderFileText("text/html");
        } else if (resource.contains("jpg")) {
            fileReader = new FileReaderFileImage("image/jpg");
        } else if (resource.contains(".js")) {
            fileReader = new FileReaderFileText("text/javascript");
        } else if (resource.contains(".css")) {
            fileReader = new FileReaderFileText("text/css");
        }
        if (fileReader != null) {
            fileReader.readFile(path, clientSocket);
        } else {
            throw new IOException();
        }
    }
}