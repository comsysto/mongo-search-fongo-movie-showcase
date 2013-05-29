package com.comsysto.playground.repository.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 5/29/13
 * Time: 4:12 PM
 */
@Document(collection = Movie.COLLECTION_NAME)
public class Movie implements Serializable {
    public static final String COLLECTION_NAME = "movie";

    @Id
    private ObjectId id;
    private String title;
    private String description;
    private String genre;
    private List<String> actors;
    private int year;
    private boolean alreadyWatched;
    private boolean likeToWatch;

    private Movie() {
    }

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAlreadyWatched() {
        return alreadyWatched;
    }

    public void setAlreadyWatched(boolean alreadyWatched) {
        this.alreadyWatched = alreadyWatched;
    }

    public boolean isLikeToWatch() {
        return likeToWatch;
    }

    public void setLikeToWatch(boolean likeToWatch) {
        this.likeToWatch = likeToWatch;
    }

    public static class Builder {

        private Movie movie = new Movie();

        private Builder(String title) {
            movie.title = title;
        };

        public static Builder create(String title) {
            return new Builder(title);
        }

        public Builder withDescription(String description) {
            movie.description = description;
            return this;
        }

        public Builder withGenre(String genre) {
            movie.genre = genre;
            return this;
        }

        public Builder withActors(List<String> actors) {
            movie.actors = actors;
            return this;
        }

        public Builder withYear(int year) {
            movie.year = year;
            return this;
        }

        public Builder withAlreadyWatched() {
            movie.alreadyWatched = true;
            return this;
        }

        public Builder withLikeToWatch() {
            movie.likeToWatch = true;
            return this;
        }

        public Movie build() {
            return movie;
        }

    }
}
