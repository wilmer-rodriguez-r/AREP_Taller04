package org.example;

import org.example.controller.MovieController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SocketServer.startSocket(35000);
    }
//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(35000);
//        } catch (IOException e) {
//            System.err.println("Could not listen on port: 35000.");
//            System.exit(1);
//        }
//        boolean running = true;
//        while (running) {
//            Socket clientSocket = null;
//            try {
//                System.out.println("Listo para recibir ...");
//                clientSocket = serverSocket.accept();
//            } catch (IOException e) {
//                System.err.println("Accept failed.");
//                System.exit(1);
//            }
//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            String inputLine, outputLine;
//            String resource = "";
//            while ((inputLine = in.readLine()) != null) {
//                if (inputLine.contains("GET")) {
//                    resource = inputLine.replaceAll("[/=?]", "").split(" ")[1];
//                    System.out.println(resource);
//                }
//                if (!in.ready()) {
//                    break;
//                }
//            }
//            String response = MovieController.getMovie(resource);
//            outputLine = "HTTP/1.1 200 OK \r\n"
//                    + "Access-Control-Allow-Origin: *\r\n"
//                    + "Content-Type: application/json \r\n"
//                    + "\r\n"
//                    + response;
//            out.println(outputLine);
//
//            out.close();
//            in.close();
//            clientSocket.close();
//        }
//        serverSocket.close();
//    }
}