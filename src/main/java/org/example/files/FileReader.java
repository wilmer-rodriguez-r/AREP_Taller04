package org.example.files;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;

public interface FileReader {

    /***
     * Obtiene el html que mostrara en el browser
     * @return un String con el html y headers
     */
    public static String getHeaders(String content_type) {
        return "HTTP/1.1 200 OK \r\n"
                + "Access-Control-Allow-Origin: *\r\n"
                + "Content-Type: "+ content_type + "\r\n"
                + "\r\n";
    }
    void readFile(URI path, Socket clientSocket) throws IOException;
}
