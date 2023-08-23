package org.example.backend;

import org.example.backend.controller.MovieController;

import java.io.*;
import java.net.*;
import java.util.Objects;

public class ClientSocket extends Thread {
    private final Socket clientSocket;

    public ClientSocket(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine, resource = "";
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("GET")) {
                    resource = inputLine.replaceAll("[/=?]", "").split(" ")[1];
                    System.out.println(resource);
                }
                if (!in.ready()) {
                    break;
                }
            }
            outputLine = getResponse(resource);
            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Oopss, something was wrong");
        }
    }

    public String getResponse(String resource) throws Exception {
        String response = MovieController.getMovie(resource);
        String headers = "HTTP/1.1 200 OK \r\n"
                + "Access-Control-Allow-Origin: *\r\n"
                + "Content-Type: application/json \r\n"
                + "\r\n";
        return headers + response;
    }
}
