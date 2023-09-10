package org.example.files;

import java.io.*;
import java.net.URI;

/***
 * Clase que se encarga de leer el archivo y devolver el resultado al cliente.
 */
public abstract class FileHandler {

    /***
     * Leerá el archivo correspondiente y lo enviará al cliente por el socket dado.
     * @param path (URI) el path donde se encuentra el archivo a leer.
     * @throws IOException En caso de que no se pueda escribir o leer el socket.
     * @return byte[] el contenido del archivo.
     */
    public abstract byte[] readFile(URI path) throws IOException;

    @Override
    public String toString() {
        return "FileReader";
    }
}
