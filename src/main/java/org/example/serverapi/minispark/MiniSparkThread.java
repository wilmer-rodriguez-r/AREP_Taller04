package org.example.serverapi.minispark;

import org.example.serverapi.files.exception.ExceptionFile;
import org.example.serverapi.minispark.handlers.Request;
import org.example.serverapi.minispark.handlers.Response;
import java.io.*;
import java.net.Socket;

/**
 * Clase que se encarga de realizar las peticiones
 */
public class MiniSparkThread extends Thread {

    private final Socket clientSocket;

    /**
     * Constructor de la clase MiniSparkThread.
     * @param clientSocket Socket del cliente por donde se recibirán las peticiones y se enviara la respuesta.
     */
    public MiniSparkThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el contenido de la petición realizada por el cliente.
     * @param in BufferedReader que se encargara de leer el contenido de la petición
     * @return byte[] será el contenido de la respuesta que se le enviara al cliente.
     * @throws IOException Cuando no se pueda leer la petición.
     * @throws ExceptionFile Cuando el archivo no se encuentre.
     */
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
        return executeLambda(headers.toString(), payload.toString());
    }

    /**
     * Ejecuta la función lambda asociada al endpoint solicitado.
     * @param headers String es el encabezado de la petición.
     * @param payload String es el body de la petición.
     * @return byte[] Corresponde a la respuesta de la petición.
     * @throws ExceptionFile Cuando el archivo no se encuentra.
     */
    public byte[] executeLambda(String headers, String payload) throws ExceptionFile {
        Request request = new Request(headers, payload);
        Response response = new Response(request.getQuery());
        response.setBody(MiniSpark.search(request.getQuery(), request.getVerb()).handle(request, response));
        return response.getResponse();
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