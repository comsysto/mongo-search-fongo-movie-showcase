package com.comsysto.playground.service.impl;

import com.comsysto.playground.repository.api.MovieRepository;
import com.comsysto.playground.repository.model.Movie;
import com.comsysto.playground.repository.query.MovieQuery;
import com.comsysto.playground.service.api.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 5/29/13
 * Time: 4:37 PM
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieDBImporter movieDBImporter;

    @Override
    public long countAll() {
        return movieRepository.countAll();
    }

    @Override
    public long countForQuery(MovieQuery query) {
        return movieRepository.countForQuery(query);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> findByQuery(MovieQuery query) {
        return movieRepository.findByQuery(query);
    }

    @Override
    public void save(Movie object) {
        movieRepository.save(object);
    }

    @Override
    public void delete(Movie object) {
        movieRepository.delete(object);
    }

    @Override
    public void deleteAll() {
        movieRepository.removeAll();
    }

    @Override
    public void importMovies (int numberOfMovies) {
        try {
            movieDBImporter.importMovies(numberOfMovies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
