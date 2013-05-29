package com.comsysto.playground.repository.api;

import com.comsysto.playground.repository.model.Movie;

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
    List<Movie> findAll();
    void removeAll();
    void delete(Movie entity);
    boolean collectionExists();
}
