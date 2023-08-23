package org.example.backend.service;

import org.example.backend.persistence.MoviePersistence;

public class MovieService {

    private static MoviePersistence moviePersistence;
    private static MovieService instance = null;
    private MovieService(MoviePersistence moviePersistence) {
        MovieService.moviePersistence = moviePersistence;
    }
    public static MovieService getInstance() {
        if(instance == null){
            instance = new MovieService(MoviePersistence.getInstance());
        }
        return instance;
    }

    public String getMovie(String movie) throws Exception {
        if (moviePersistence.containsMovie(movie)) {
            return moviePersistence.getMovie(movie);
        } else {
            throw new Exception("Not found");
        }
    }

    public void addMovie(String movie, String data) throws Exception {
        if (!moviePersistence.containsMovie(movie)) {
            moviePersistence.addMovie(movie, data);
        } else {
            throw new Exception("The movie already existed");
        }
    }
}
