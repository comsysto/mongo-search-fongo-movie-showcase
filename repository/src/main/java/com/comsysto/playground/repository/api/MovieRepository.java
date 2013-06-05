package com.comsysto.playground.repository.api;

import com.comsysto.playground.repository.model.Movie;
import com.comsysto.playground.repository.query.MovieQuery;

import java.util.List;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 5/29/13
 * Time: 4:31 PM
 */
public interface MovieRepository {
    void save(Movie entity);
    void dropCollection();
    long countAll();
    long countForQuery(MovieQuery query);
    List<Movie> findAll();
    List<Movie> findByQuery(MovieQuery query);
    void removeAll();
    void delete(Movie entity);
    boolean collectionExists();

}
