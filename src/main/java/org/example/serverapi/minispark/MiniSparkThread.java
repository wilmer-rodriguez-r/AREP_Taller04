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
            byte[] response = getResponse();
            sendElement(response);
        } catch (Exception e) {
            try {
                byte[] response = executeLambda("GET /NotFound.html");
                sendElement(response);
            } catch (ExceptionFile | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public byte[] getResponse() throws IOException, ExceptionFile {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine = in.readLine();
        System.out.println("Received: " + inputLine);
        return executeLambda(inputLine);
    }

    public byte[] executeLambda(String inputLine) throws ExceptionFile {
        Request request = new Request(inputLine);
        Response response = new Response(request.getResource());
        response.setBody(MiniSpark.search(request.getResource()).readFile(request, response));
        return response.getResponse();
    }

    public void sendElement(byte[] response) throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        out.write(response);
        out.close();
    }

}
