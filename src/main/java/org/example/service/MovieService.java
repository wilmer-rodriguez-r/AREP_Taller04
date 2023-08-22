package org.example.service;

import org.example.controller.MovieController;

import java.io.IOException;

public class MovieService {

    public String getMovie(String movie) throws IOException {
        return MovieController.getMovie(movie);
    }
}
