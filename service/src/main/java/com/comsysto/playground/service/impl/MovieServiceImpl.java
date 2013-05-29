package com.comsysto.playground.service.impl;

import com.comsysto.playground.repository.api.MovieRepository;
import com.comsysto.playground.repository.model.Movie;
import com.comsysto.playground.service.api.MovieService;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.MovieDb;
import com.omertron.themoviedbapi.results.TmdbResultsList;
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

    // API Key
    private static final String API_KEY = "16a0036a641140ce7e23ddd423dfbf50";
    private static TheMovieDbApi tmdb;

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

    @Override
    public void deleteAll() {
        movieRepository.removeAll();
    }

    @Override
    public void importMovies () {
        try {
            TheMovieDbApi tmdb = new TheMovieDbApi(API_KEY);

            //TmdbResultsList<MovieDb> results = tmdb.getNowPlayingMovies("", 0);

            TmdbResultsList<Genre> genreList = tmdb.getGenreList("");

            for (Genre genre : genreList.getResults()) {

                TmdbResultsList<MovieDb> results = tmdb.getGenreMovies(genre.getId(), "", 0, true);

                for(MovieDb result: results.getResults()) {

                    Movie movie = Movie.Builder.create(result.getTitle()).withGenre(genre.getName()).build();

                    movieRepository.save(movie);

                    System.out.println(result);
                }
            }

        } catch (MovieDbException e) {
            System.out.println("MovieDbException in importMovies()");
            e.printStackTrace();
        }

    }

}
