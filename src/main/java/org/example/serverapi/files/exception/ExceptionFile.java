package org.example.serverapi.files.exception;

/***
 * Clase que retorna excepciones cuando los archivos no se leen correctamente
 */
public class ExceptionFile extends Exception{
    public static String NOT_FOUND = "We can not found the resource required";

    public ExceptionFile(String message) {
        super(message);
    }
}
