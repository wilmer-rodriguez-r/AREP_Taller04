package org.example.clientTestConcurrency;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

/***
 * Clase que extiend de Thread para poder tener varias peticiones concurrentemente
 */
public class RequestThread extends Thread{

    private URL url;
    private AtomicBoolean requestOk;

    /***
     * Constructor de la clase RequestThread
     * @param url recibe una URL a la que hara la consulta
     * @param requestOk un AtomicBoolean que dira si el test falla
     */
    public RequestThread(URL url, AtomicBoolean requestOk) {
        this.url = url;
        this.requestOk = requestOk;
    }
    @Override
    public void run() {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(!(urlConnection.getResponseCode() == 200)){
                requestOk.set(false);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
