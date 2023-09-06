package org.example.movies;

import org.example.movies.controller.MovieController;

import java.io.*;
import java.net.*;

/***
 * Clase que se encarga de manejar varios clients por medio de Threads
 */
public class ClientSocket extends Thread {
    private final Socket clientSocket;

    /***
     * Constructor de la clase ClientSocket
     * @param socket recibe un Socket correspondiente al que le da el servidor
     */
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

    /***
     * Devuelve una respuesta lista para enviar por el socket
     * @param resource un string que corresponde al recurso que se esta consultando
     * @return un String con el request listo para enviar
     * @throws Exception en caso de que no se encuentre el recurso solicitado
     */
    private String getResponse(String resource) throws Exception {
        String response = MovieController.getMovie(resource);
        String headers = "HTTP/1.1 200 OK \r\n"
                + "Access-Control-Allow-Origin: *\r\n"
                + "Content-Type: application/json \r\n"
                + "\r\n";
        return headers + response;
    }
}
