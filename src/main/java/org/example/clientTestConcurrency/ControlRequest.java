package org.example.clientTestConcurrency;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/***
 * Clase que hace de controlador entre los hilos que realizan las consultas
 */

public class ControlRequest {
    private AtomicBoolean requestOk = new AtomicBoolean(true);
    private String[] movies;
    private String url;

    /***
     * Constructor de la clase Control Request
     * @param numRequest un int que es la cantidad de peticiones a realizar
     * @param url un String que sera el destino donde se haran las peticiones
     * @param movies un String[] que dara diferentes peliculas a consultar
     * @throws MalformedURLException en caso de que la URL este mal
     * @throws InterruptedException en caso de que el test haya fallado por una consulta mal hecha
     */
    public ControlRequest(int numRequest, String url, String[] movies) throws MalformedURLException, InterruptedException {
        this.url = url;
        this.movies = movies;
        startTest(numRequest);
    }

    /***
     * Clase que ejecuta el test creando varios hilos que haran consultas
     * @param numRequest la cantidad de peticiones a realizar
     * @throws InterruptedException en caso e que el test falle
     * @throws MalformedURLException si la URL que se provee esta mal
     */
    public void startTest(int numRequest) throws InterruptedException, MalformedURLException {
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < numRequest; i++) {
            if(requestOk.get()) {
                Random random = new Random();
                URL url_ = new URL(this.url + movies[random.nextInt(movies.length)]);
                RequestThread thread = new RequestThread(url_, requestOk);
                thread.start();
                thread.join();
            }
        }
        long endTime = (System.currentTimeMillis() - startTime);
        System.out.println("Respuesta de los request: " + requestOk + "\n" +
                "Tiempo del test: " + endTime+ "ms");
    }
}
