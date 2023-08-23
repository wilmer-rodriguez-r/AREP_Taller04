package org.example.clientTestConcurrency;

/***
 * Clase principal para el test de concurrencia
 */
public class MainTest {
    /***
     * Función main que ejecuta el test de concurrencia
     * @param args un String[] donde puede recibir parametros
     */
    public static void main(String[] args) {
        try {
            String url = "http://localhost:35000/";
            String[] movies = {"Terminator", "John Wick", "Pulp Fiction", "Looney Tunes", "Creed"};
            int numRequest = 10000;
            ControlRequest test = new ControlRequest(numRequest, url, movies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}