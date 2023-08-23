package org.example.testConcurrency;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlRequest {
    private AtomicBoolean requestOk = new AtomicBoolean(true);
    private String[] movies;
    private String url;
    public ControlRequest(int numRequest, String url, String[] movies) throws MalformedURLException, InterruptedException {
        this.url = url;
        this.movies = movies;
        startTest(numRequest);
    }
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
        long endTime = (System.currentTimeMillis() - startTime)/1000;
        System.out.println(endTime);
    }
}
