package backend;


import org.example.backend.persistence.MoviePersistence;
import org.example.backend.service.MovieService;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
public class ServicesTests {

    MovieService movieService;
    MoviePersistence moviePersistence;

    @Before
    public void init() {
        moviePersistence = MoviePersistence.getInstance();
        movieService = MovieService.getInstance();
    }
    @Test
    public void when_MovieIsInCache_then_ReturnData() throws Exception {
        //Arrange
        moviePersistence.addMovie("It", "{\"Title\":\"It\",\"Year\":\"2017\",\"Rated\":\"R\",\"Released\":\"08 Sep 2017\",\"Runtime\":\"135 min\",\"Genre\":\"Horror\",\"Director\":\"Andy Muschietti\",\"Writer\":\"Chase Palmer, Cary Joji Fukunaga, Gary Dauberman\",\"Actors\":\"Bill Skarsgård, Jaeden Martell, Finn Wolfhard\",\"Plot\":\"In the summer of 1989, a group of bullied kids band together to destroy a shape-shifting monster, which disguises itself as a clown and preys on the children of Derry, their small Maine town.\",\"Language\":\"English, Hebrew\",\"Country\":\"United States, Canada\",\"Awards\":\"10 wins & 46 nominations\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZDVkZmI0YzAtNzdjYi00ZjhhLWE1ODEtMWMzMWMzNDA0NmQ4XkEyXkFqcGdeQXVyNzYzODM3Mzg@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.3/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"86%\"},{\"Source\":\"Metacritic\",\"Value\":\"69/100\"}],\"Metascore\":\"69\",\"imdbRating\":\"7.3\",\"imdbVotes\":\"576,995\",\"imdbID\":\"tt1396484\",\"Type\":\"movie\",\"DVD\":\"19 Dec 2017\",\"BoxOffice\":\"$328,874,981\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}");
        //ACT
        String data = movieService.getMovie("It");
        //ASSERT
        assertEquals(data, moviePersistence.getMovie("It"));
    }

    @Test(expected = Exception.class)
    public void when_MovieIsNotInCache_then_ThrowException() throws Exception {
        //Arrange
        //ACT
        String data = movieService.getMovie("Looney Tunes");
        //ASSERT
    }

    @Test
    public void when_MovieIsNew_then_AddToCache() throws Exception {
        //Arrange
        String data = "{author: wilmer}, {year: 2001}";
        //ACT
        movieService.addMovie("Pulp Fiction","{author: wilmer}, {year: 2001}");
        //ASSERT
        assertEquals(data, moviePersistence.getMovie("Pulp Fiction"));
    }

    @Test(expected = Exception.class)
    public void when_MovieIsAlreadyExist_then_ThrowException() throws Exception {
        //Arrange
        String data = "{author: wilmer}, {year: 2001}";
        moviePersistence.addMovie("Creed", data);
        //ACT
        movieService.addMovie("Creed","{author: wilmer}, {year: 2001}");
        //ASSERT
    }

}
