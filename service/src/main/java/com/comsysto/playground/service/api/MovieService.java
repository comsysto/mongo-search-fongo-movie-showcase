package com.comsysto.playground.service.api;

import com.comsysto.playground.repository.model.Movie;
import com.comsysto.playground.repository.query.MovieQuery;

import java.util.List;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 5/29/13
 * Time: 4:37 PM
 */
public interface MovieService {

    long countAll();
    long countForQuery(MovieQuery query);
    List<Movie> findAll();
    List<Movie> findByQuery(MovieQuery query);
    void save(Movie object);
    void delete(Movie object);
    void deleteAll();
    void importMovies(int numberOfMovies, boolean dropOldEntries);

}
