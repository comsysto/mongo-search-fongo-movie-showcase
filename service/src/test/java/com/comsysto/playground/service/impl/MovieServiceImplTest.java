package com.comsysto.playground.service.impl;

import com.comsysto.playground.repository.model.Movie;
import com.comsysto.playground.repository.query.MovieQuery;
import com.comsysto.playground.service.api.MovieService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static junit.framework.Assert.*;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 5/29/13
 * Time: 4:40 PM
 */
@ContextConfiguration(locations = "classpath:com/comsysto/playground/service/spring-context.xml")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MovieServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    MovieService movieService;

    @Test
    public void testCreateFindAndDeleteMovie() {
        // not required when using fongo!
        movieService.deleteAll();

        Movie movie = Movie.MovieBuilder.create("Movie").build();
        movieService.save(movie);
        List<Movie> retrievedMovies = movieService.findAll();

        assertEquals(1, retrievedMovies.size());
        assertEquals(movie.getTitle(), retrievedMovies.get(0).getTitle());

        movieService.delete(retrievedMovies.get(0));
        retrievedMovies = movieService.findAll();
        assertEquals(0, retrievedMovies.size());
    }

    @Ignore // takes too long to run during build
    @Test
    public void testImportMovies() {
        // not required when using fongo!
        movieService.deleteAll();

        movieService.importMovies();

        List<Movie> retrievedMovies = movieService.findAll();
        assertNotSame(0, retrievedMovies.size());
    }

    @Test
    public void testFindByQuery() {
        // not required when using fongo!
        movieService.deleteAll();

        Movie movie1 = randomMovie("", "Action", true);
        Movie movie2 = randomMovie("", "No Action", true);
        Movie movie3 = randomMovie("", "No Action", false);
        Movie movie4 = randomMovie("", "Action", true);
        Movie movie5 = randomMovie("", "Action", false);

        movieService.save(movie1);
        movieService.save(movie2);
        movieService.save(movie3);
        movieService.save(movie4);
        movieService.save(movie5);

        MovieQuery query = MovieQuery.MovieQueryBuilder.create()
                .withGenre("Action")
                .withAlreadyWatched(true)
                .build();

        List<Movie> queryResult = movieService.findByQuery(query);

        assertEquals(2, queryResult.size());
        String firstResultTitle = queryResult.get(0).getTitle();
        String secondResultTitle = queryResult.get(1).getTitle();
        assertTrue((firstResultTitle.equals(movie1.getTitle()) && secondResultTitle.equals(movie4.getTitle()))
            || firstResultTitle.equals(movie4.getTitle()) && secondResultTitle.equals(movie1.getTitle()));
    }

    @Ignore // full text search does not work with fongo
    @Test
    public void testFindByQueryFullTextSearch() {
        // not required when using fongo!
        movieService.deleteAll();

        Movie movie1 = randomMovie("Searching", "Action", true);
        Movie movie2 = randomMovie("", "No Action", true);
        Movie movie3 = randomMovie("", "No Action", false);
        Movie movie4 = randomMovie("", "Action", true);
        Movie movie5 = randomMovie("", "Action", false);

        movieService.save(movie1);
        movieService.save(movie2);
        movieService.save(movie3);
        movieService.save(movie4);
        movieService.save(movie5);

        MovieQuery query = MovieQuery.MovieQueryBuilder.create()
                .withTitleFullTextSearch("search") // make sure stemming works
                .withGenre("Action")
                .withAlreadyWatched(true)
                .build();

        List<Movie> queryResult = movieService.findByQuery(query);

        assertEquals(1, queryResult.size());
        assertTrue(queryResult.get(0).getTitle().equals(movie1.getTitle()));
    }

    private Movie randomMovie(String partOfTitle, String genre, boolean alreadyWatched) {
        Random random = new Random();
        return Movie.MovieBuilder.create(partOfTitle+" Random "+new BigInteger(20, random).toString(32))
                .withGenre(genre)
                .withAlreadyWatched(alreadyWatched)
                .build();
    }
}
