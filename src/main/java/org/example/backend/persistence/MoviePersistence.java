package org.example.backend.persistence;

import java.util.concurrent.ConcurrentHashMap;

public class MoviePersistence {

    private final ConcurrentHashMap<String, String> movieCache;
    private static MoviePersistence instance = null;

    private MoviePersistence() {
        this.movieCache = new ConcurrentHashMap<>();
    }

    public static MoviePersistence getInstance() {
        if(instance == null){
            instance = new MoviePersistence();
        }
        return instance;
    }

    public void addMovie(String movieTitle, String data) {
        movieCache.put(movieTitle, data);
    }

    public String getMovie(String movieTitle) {
        return movieCache.get(movieTitle);
    }

    public boolean containsMovie(String movieTitle) {
        return movieCache.containsKey(movieTitle);
    }
}
