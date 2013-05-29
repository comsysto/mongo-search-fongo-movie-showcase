package com.comsysto.playground.service.api;

import com.comsysto.playground.repository.model.Movie;

import java.util.List;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 5/29/13
 * Time: 4:37 PM
 */
public interface MovieService {

    long countAll();
    List<Movie> findAll();
    void save(Movie object);
    void delete(Movie object);

}
