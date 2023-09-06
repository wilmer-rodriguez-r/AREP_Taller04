package org.example.serverapi.minispark;

import org.example.serverapi.files.File;
import org.example.serverapi.files.exception.ExceptionFile;
import org.example.serverapi.minispark.handlers.Request;
import org.example.serverapi.minispark.handlers.Response;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class MiniSparkThread extends Thread {

    private final Socket clientSocket;
    public MiniSparkThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            try {
                byte[] response = getResponse(in);
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

    public byte[] getResponse(BufferedReader in) throws IOException, ExceptionFile {
        StringBuilder headers = new StringBuilder();
        String headerLine = null;
        while(!(headerLine = in.readLine()).isEmpty()){
            headers.append(headerLine).append("\n");
        }
        StringBuilder payload = new StringBuilder();
        while(in.ready()){
            payload.append((char) in.read());
        }
        return executeLambda(headers.toString(), payload.toString());
    }

    public byte[] executeLambda(String headers, String payload) throws ExceptionFile, IOException {
        Request request = new Request(headers, payload);
        Response response = new Response(request.getResource());
        response.setBody(MiniSpark.search(request.getResource(), request.getVerb()).readFile(request, response));
        return response.getResponse();
    }

    public void sendElement(byte[] response, Socket clientSocket) throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        out.write(response);
        out.close();
    }
}