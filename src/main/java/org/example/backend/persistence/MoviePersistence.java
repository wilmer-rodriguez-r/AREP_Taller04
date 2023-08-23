package org.example.backend.persistence;

import java.util.concurrent.ConcurrentHashMap;

/***
 * Clase que se encarga de actuar como un cache, ademas de guardar y mantener la integridad de los datos de las peliculas
 */
public class MoviePersistence {

    private final ConcurrentHashMap<String, String> movieCache;
    private static MoviePersistence instance = null;

    /***
     * Constructor de la clase MoviePersistence
     */
    private MoviePersistence() {
        this.movieCache = new ConcurrentHashMap<>();
    }

    /***
     * Singleton de la clase MoviePersistence
     * @return un objeto de tipo MoviePersistence que la instancia del mismo.
     */
    public static MoviePersistence getInstance() {
        if(instance == null){
            instance = new MoviePersistence();
        }
        return instance;
    }

    /***
     * Agregar una nueva pelicula al cache
     * @param movieTitle un String que corresponde al titulo de la pelicula
     * @param data un String que son los datos correspondientes a la pelicula
     */
    public void addMovie(String movieTitle, String data) {
        movieCache.put(movieTitle, data);
    }

    /***
     * Traer los datos de una pelicula que se encuentra en cache
     * @param movieTitle un String que corresponde al titulo de la pelicula
     * @return un String con los datos de la pelicula
     */
    public String getMovie(String movieTitle) {
        return movieCache.get(movieTitle);
    }

    /***
     * Verifica si una pelicula se encuentra en cache
     * @param movieTitle un String que corresponde al titulo de la pelicula
     * @return un boolean que confirma si esta o no la pelicula en cache
     */
    public boolean containsMovie(String movieTitle) {
        return movieCache.containsKey(movieTitle);
    }
}
