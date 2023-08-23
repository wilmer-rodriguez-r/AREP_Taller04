package org.example.testConcurrency;

public class MainTest {
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