package com.comsysto.playground.service.impl;

import com.comsysto.playground.repository.model.Movie;
import com.comsysto.playground.service.api.MovieService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 5/29/13
 * Time: 4:40 PM
 */
@ContextConfiguration(locations = "classpath:com/comsysto/playground/service/spring-test-context.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MovieServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    MovieService movieService;

    @Test
    public void testCreateFindAndDeleteMovie() {
        Movie movie = Movie.Builder.create("Movie").build();
        movieService.save(movie);
        List<Movie> retrievedMovies = movieService.findAll();

        assertEquals(1, retrievedMovies.size());
        assertEquals(movie.getTitle(), retrievedMovies.get(0).getTitle());

        movieService.delete(retrievedMovies.get(0));
        retrievedMovies = movieService.findAll();
        assertEquals(0, retrievedMovies.size());
    }

    @Test
    public void testImportMovies() {

        movieService.deleteAll();

        movieService.importMovies();

        List<Movie> retrievedMovies = movieService.findAll();

        assertNotSame(0, retrievedMovies.size());
    }
}
