package org.example.miniSpring;

import org.example.files.exception.ExceptionFile;
import org.example.handlers.Request;
import org.example.handlers.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MiniSpringServer extends Thread {

    private final int port;
    public static final String path = "./target/classes/public";

    public MiniSpringServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Listening ...");
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                try {
                    byte[] response = getRequest(in);
                    sendElement(response, clientSocket);
                } catch (Exception e) {
                    Response response = new Response("GET /NotFound.html");
                    response.badRequest();
                    sendElement(response.getResponse(), clientSocket);
                }
                in.close();
                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not listen on port: " + port);
        }
    }

    public byte[] getRequest(BufferedReader in) throws IOException, ExceptionFile {
        StringBuilder headers = new StringBuilder();
        String headerLine;
        while(!(headerLine = in.readLine()).isEmpty()){
            headers.append(headerLine).append("\n");
        }
        StringBuilder payload = new StringBuilder();
        while(in.ready()){
            payload.append((char) in.read());
        }
        return executeMethod(headers.toString(), payload.toString());
    }

    /**
     * Ejecuta la función lambda asociada al endpoint solicitado.
     * @param headers String es el encabezado de la petición.
     * @param payload String es el body de la petición.
     * @return byte[] Corresponde a la respuesta de la petición.
     */
    public byte[] executeMethod(String headers, String payload) throws ExceptionFile {
        try {
            Request request = new Request(headers, payload);
            Response response = new Response(request.getEndpoint());
            request.setFile(response.isFile());
            response.setBody(LoadComponents.invokeMethod(request));
            return response.getResponse();
        } catch (Exception e) {
            throw new ExceptionFile(ExceptionFile.NOT_FOUND);
        }
    }

    /**
     * Función que enviara la respuesta por el socket para el cliente.
     * @param response byte[] que es el la respuesta a enviar.
     * @param clientSocket Socket es la conexión con el cliente.
     * @throws IOException En caso de que no se pueda enviar la respuesta.
     */
    public void sendElement(byte[] response, Socket clientSocket) throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        out.write(response);
        out.close();
    }
}
