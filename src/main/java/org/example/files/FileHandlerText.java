package org.example.files;

import java.io.*;
import java.net.URI;
import java.util.Scanner;
/***
 * Clase qu se encarga de leer los archivos texto y enviarlos al cliente.
 */
public class FileHandlerText extends FileHandler {


    /***
     * Leerá el archivo correspondiente y lo enviará al cliente por el socket dado.
     * @param path (URI) el path donde se encuentra el archivo a leer.
     * @throws IOException En caso de que no se pueda escribir o leer el socket.
     */
    public byte[] readFile(URI path) throws IOException {
        File file = new File(path.getPath());
        StringBuilder outputLine = new StringBuilder();
        if (file.exists()) {
            Scanner line = new Scanner(file);
            while (line.hasNext()) {
                outputLine.append(line.nextLine()).append("\n");
            }
            line.close();
        }
        return outputLine.toString().getBytes();
    }

    @Override
    public String toString() {
        return "File Text";
    }
}
