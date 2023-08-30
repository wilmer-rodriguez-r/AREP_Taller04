package org.example.files;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.util.Scanner;

/***
 * Clase que se encarga de leer el archivo y devolver el resultado al cliente.
 */
public abstract class FileReader {

    protected final String content_types;

    protected FileReader(String contentTypes) {
        content_types = contentTypes;
    }

    /***
     * Obtiene el html que mostrara en el browser.
     * @return un String con el html y headers.
     */
    protected static String getHeaders(String content_type) {
        return "HTTP/1.1 200 OK \r\n"
                + "Access-Control-Allow-Origin: *\r\n"
                + "Content-Type: "+ content_type + "\r\n"
                + "\r\n";
    }

    /***
     * Obtiene el html que mostrara en el browser.
     * @return un String con el html y headers.
     */
    private static String getHeadersBadRequest() {
        return "HTTP/1.1 404 Not Found \r\n" +
                "Content-Type: text/html \r\n" +
                "\r\n";
    }

    /***
     * Leera el archivo correspondiente y lo enviará al cliente por el socket dado.
     * @param path (URI) el path donde se encuentra el archivo a leer.
     * @param clientSocket (Socket) el socket donde se envía la respuesta al cliente.
     * @throws IOException En caso de que no se pueda escribir o leer el socket.
     */
    public abstract void readFile(URI path, Socket clientSocket) throws IOException;

    /***
     * Respuesta que se le manda al cliente en caso de que no se encuentre el recurso solicitado.
     * @param clientSocket (Socket) el socket donde el cliente escuchara.
     * @throws IOException En caso de que no se pueda escribir o leer el socket.
     */
    public static void badRequest(Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        File file = new File("./target/classes/public/NotFound.html");
        StringBuilder outputLine = new StringBuilder();
        if (file.exists()) {
            Scanner line = new Scanner(file);
            while (line.hasNext()) {
                outputLine.append(line.nextLine()).append("\n");
            }
            line.close();
        }
        out.println(FileReader.getHeadersBadRequest() + outputLine);
        out.close();
    }

    /***
     * Obtiene el MIME type correspondiente.
     * @return (String) el MIME type.
     */
    public String getContent_types() {
        return content_types;
    }
}
