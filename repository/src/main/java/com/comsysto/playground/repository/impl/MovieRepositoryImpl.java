package com.comsysto.playground.repository.impl;

import com.comsysto.playground.repository.api.MovieRepository;
import com.comsysto.playground.repository.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 5/29/13
 * Time: 4:30 PM
 */
@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Autowired
    private MongoOperations mongoOperations;
    private Class<Movie> clazz = Movie.class;

    @Override
    public void save(Movie entity) {
        mongoOperations.save(entity);
    }

    @Override
    public void dropCollection() {
        mongoOperations.dropCollection(clazz);
    }

    @Override
    public long countAll() {
        return mongoOperations.count(new Query(), clazz);
    }

    @Override
    public List<Movie> findAll() {
        return mongoOperations.findAll(clazz);
    }

    @Override
    public void removeAll() {
        mongoOperations.remove(new Query(), clazz);
    }

    @Override
    public void delete(Movie entity) {
        mongoOperations.remove(entity);
    }

    @Override
    public boolean collectionExists() {
        return mongoOperations.collectionExists(clazz);
    }

}
