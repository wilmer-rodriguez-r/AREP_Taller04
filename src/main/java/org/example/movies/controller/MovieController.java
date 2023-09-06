package org.example.movies.controller;

import org.example.movies.service.MovieService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/***
 * Esta clase se encarga de hacer las peticiones para el recurso de movies
 */
public class MovieController {
    private static final String GET_URL = "http://www.omdbapi.com/?apikey=f204c1de&";
    private static MovieService movieService;
    private static MovieController instance = null;

    /***
     * Constructor de la clase Movie controller
     * @param movieService recibe la clase correspondiente al servicio del recurso movies
     */
    private MovieController(MovieService movieService) {
        MovieController.movieService = movieService;
    }


    /***
     * Singleton donde se genera solo una instancia de esta clase.
     * @return la instancia de MovieController
     */
    public static MovieController getInstance() {
        if (instance == null) {
            instance = new MovieController(MovieService.getInstance());
        }
        return instance;
    }

    /***
     * Obtiene la película del servicio de movies, en caso de no tenerla consulta a la API externa
     * @param movie un String con el nombre o título de la película
     * @return un String con los datos relacionados con la película
     * @throws Exception cuando la película no existe.
     */
    public static String getMovie(String movie) throws Exception {
        try {
            return movieService.getMovie(movie);
        } catch (Exception e) {
            URL obj = new URL(GET_URL + "t=" + movie);
            String response = makeRequest(obj);
            movieService.addMovie(movie, response);
            return response;
        }
    }

    /***
     * Función que se encarga de realizar las consultas a la API externa
     * @param obj una URL a la que se le realiza la consulta
     * @return un String con los datos solicitados a dicha consulta
     * @throws IOException en caso de fallar la consulta
     */
    private static String makeRequest(URL obj) throws IOException {
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
            return "GET request not worked";
        }
    }
}
