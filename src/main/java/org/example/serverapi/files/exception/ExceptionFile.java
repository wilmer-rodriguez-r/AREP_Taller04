package org.example.serverapi.files.exception;

/***
 * Clase que retorna excepciones cuando los archivos no se leen correctamente
 */
public class ExceptionFile extends Exception{

    /**
     * En caso de que no se encuentre el archivo correspondiente.
     */
    public static String NOT_FOUND = "We can not found the resource required";

    /**
     * Constructor de la clase.
     * @param message String con el mensaje de la excepción.
     */
    public ExceptionFile(String message) {
        super(message);
    }
}
