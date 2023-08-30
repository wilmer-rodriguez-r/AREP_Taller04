package org.example.files.filesFactory;

import org.example.files.FileReader;
import org.example.files.exception.ExceptionFile;

/***
 * Interfaz de la fabrica
 */
public interface FilesFactoryInterface {

    /***
     * Obtienen una instancia de clase correspondiente al recurso que se solicita
     * @param resource (String) nombre del recurso solicitado
     * @return (FileReader) una instancia que pueda leer el archivo solicitado
     * @throws ExceptionFile En caso de que no se encuentre el archivo
     */
    FileReader getInstance(String resource) throws ExceptionFile;

}
