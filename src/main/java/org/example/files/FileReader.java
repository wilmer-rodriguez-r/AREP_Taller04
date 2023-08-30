package org.example.files;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.util.Scanner;

public abstract class FileReader {

    protected final String content_types;

    protected FileReader(String contentTypes) {
        content_types = contentTypes;
    }

    /***
     * Obtiene el html que mostrara en el browser
     * @return un String con el html y headers
     */
    protected static String getHeaders(String content_type) {
        return "HTTP/1.1 200 OK \r\n"
                + "Access-Control-Allow-Origin: *\r\n"
                + "Content-Type: "+ content_type + "\r\n"
                + "\r\n";
    }

    public abstract void readFile(URI path, Socket clientSocket) throws IOException;
}
