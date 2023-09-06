package org.example.movies.service;

import org.example.movies.persistence.MoviePersistence;

/***
 * Clase que se encarga de comunicarse con la persistencia del recurso movies
 */
public class MovieService {

    private static MoviePersistence moviePersistence;
    private static MovieService instance = null;

    /***
     * Constructor de la clase MovieService
     * @param moviePersistence una instancia de MoviePersistence
     */
    private MovieService(MoviePersistence moviePersistence) {
        MovieService.moviePersistence = moviePersistence;
    }

    /***
     * Singleton de la clase MovieService
     * @return un objeto de tipo MovieService que la instancia del mismo.
     */
    public static MovieService getInstance() {
        if(instance == null){
            instance = new MovieService(MoviePersistence.getInstance());
        }
        return instance;
    }

    /***
     * Obtiene los datos de una película del cache
     * @param movie un String que corresponde al nombre de la película
     * @return un String con los datos de la película consultada
     * @throws Exception en caso de que la película no se encuentre en cache
     */
    public String getMovie(String movie) throws Exception {
        if (moviePersistence.containsMovie(movie)) {
            return moviePersistence.getMovie(movie);
        } else {
            throw new Exception("Not found");
        }
    }

    /***
     * Agrega una película al cache
     * @param movie un String que corresponde al nombre de la película
     * @param data los datos de la película
     * @throws Exception en caso de que la película ya este en cache
     */
    public void addMovie(String movie, String data) throws Exception {
        if (!moviePersistence.containsMovie(movie)) {
            moviePersistence.addMovie(movie, data);
        } else {
            throw new Exception("The movie already existed");
        }
    }
}
