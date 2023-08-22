package org.example.controller;

import org.example.service.MovieService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/***
 *
 */
public class MovieController {
    private static final String GET_URL = "http://www.omdbapi.com/?apikey=f204c1de&";
    private static MovieService movieService;
    private static MovieController instance = null;

    private MovieController(MovieService movieService) {
        MovieController.movieService = movieService;
    }

    public static MovieController getInstance() {
        if (instance == null) {
            instance = new MovieController(MovieService.getInstance());
        }
        return instance;
    }

    public static String getMovie(String movie) throws Exception {
        try {
            return MovieService.getMovie(movie);
        } catch (Exception e) {
            URL obj = new URL(GET_URL + "t=" + movie);
            String response = makeRequest(obj);
            MovieService.addMovie(movie, response);
            return response;
        }
    }

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
