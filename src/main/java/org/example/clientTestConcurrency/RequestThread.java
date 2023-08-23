package org.example.clientTestConcurrency;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestThread extends Thread{

    private URL url;
    private AtomicBoolean requestOk;
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
