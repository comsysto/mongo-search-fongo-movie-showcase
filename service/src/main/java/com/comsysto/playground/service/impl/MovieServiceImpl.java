package com.comsysto.playground.service.impl;

import com.comsysto.playground.repository.api.MovieRepository;
import com.comsysto.playground.repository.model.Movie;
import com.comsysto.playground.service.api.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public long countAll() {
        return movieRepository.countAll();
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public void save(Movie object) {
        movieRepository.save(object);
    }

    @Override
    public void delete(Movie object) {
        movieRepository.delete(object);
    }

}
