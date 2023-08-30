package org.example;

/***
 * Clase principal que se encarga de desplegar el servidor http y el backend
 */
public class Main {

    /***
     * Función que ejecuta el servidor http y el backend en sus repectivos puertos.
     * @param args un String[] donde puede recibir parametros
     */
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(5500);
        httpServer.run();
    }

}