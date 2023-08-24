package org.example.frontend;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.file.*;
import java.util.Scanner;

import static org.example.frontend.HttpServer.HttpRequestThread.leerArchivo;

/***
 * Clase que actua de servidor HTTP
 */
public class HttpServer extends Thread {

    private final int port;

    /***
     * Constructor de la clase HttpServer
     * @param port un int que corresponde al puerto donde correra
     */
    public HttpServer(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    System.out.println("Listening ...");
                    new HttpRequestThread(serverSocket.accept()).start();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: 35000.");
        }
    }

    /***
     * Obtiene el html que mostrara en el browser
     * @return un String con el html y headers
     */
    public static String getHeaders(){
        return "HTTP/1.1 200 OK \r\n"
                + "Access-Control-Allow-Origin: *\r\n"
                + "Content-Type: text/html \r\n"
                + "\r\n";
    }

    /***
     * Clase embebida en HttpServer que se ejecuta para mantener la concurrencia del servidor
     */
    static class HttpRequestThread extends Thread{
        private static Socket clientSocket = null;

        /***
         * Constructor de la clase HttpRequestThread
         * @param socket un Socket que le otorga el servidor para comunicarse con el
         */
        public HttpRequestThread(Socket socket) {
            clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                leerArchivo("a");
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("Oopss, something was wrong");
            }
        }
        public static void leerArchivo(String path) throws IOException {
            path = "./target/classes/index.html";
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            File file = new File(path);
            StringBuilder outputLine = new StringBuilder();
            if (file.exists()) {
                Scanner line = new Scanner(file);
                while (line.hasNext()) {
                    outputLine.append(line.nextLine()).append("\n");
                }
                line.close();
            } else {
                System.out.println("¡El fichero no existe!");
            }
            out.println(getHeaders() + outputLine);
            out.close();
        }
    }
}