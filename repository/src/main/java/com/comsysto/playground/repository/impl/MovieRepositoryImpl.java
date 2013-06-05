package com.comsysto.playground.repository.impl;

import com.comsysto.playground.repository.api.MovieRepository;
import com.comsysto.playground.repository.model.Movie;
import com.comsysto.playground.repository.query.MovieQuery;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

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

    @PostConstruct
    public void createTextIndex() {
        // make sure the index is set up properly (not yet possible via Spring Data)
        mongoOperations.getCollection(Movie.COLLECTION_NAME).ensureIndex(new BasicDBObject("title", "text"));
    }

    @Override
    public void save(Movie entity) {
        mongoOperations.save(entity);
    }

    @Override
    public void delete(Movie entity) {
        mongoOperations.remove(entity);
    }

    @Override
    public void removeAll() {
        mongoOperations.remove(new Query(), clazz);
    }

    @Override
    public boolean collectionExists() {
        return mongoOperations.collectionExists(clazz);
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
    public long countForQuery(MovieQuery query) {
        if (query.getTitleFullTextSearch() != null) {
            return countForFullTextSearchQuery(query);
        }
        return mongoOperations.count(mapQuery(query), clazz);
    }

    private long countForFullTextSearchQuery(MovieQuery query) {
        // for all we know mongo full text search does not yet support a count method
        return findByFullTextSearchQuery(query).size();
    }

    @Override
    public List<Movie> findAll() {
        return mongoOperations.findAll(clazz);
    }

    @Override
    public List<Movie> findByQuery(MovieQuery query) {
        if (query.getTitleFullTextSearch() != null) {
            return findByFullTextSearchQuery(query);
        }
        return mongoOperations.find(mapQuery(query), clazz);
    }

    private List<Movie> findByFullTextSearchQuery(MovieQuery query) {
        CommandResult commandResult = executeFullTextSearch(query.getTitleFullTextSearch(), mapQuery(query));
        Collection<ObjectId> searchResultIds = extractSearchResultIds(commandResult);
        Criteria criteria = Criteria.where("_id").in(searchResultIds);
        Query objectIdQuery = Query.query(criteria);
        applySortAndPagination(query, objectIdQuery);
        return mongoOperations.find(objectIdQuery, clazz);
    }

    private CommandResult executeFullTextSearch(String searchString, Query filter) {
        BasicDBObject textSearch = new BasicDBObject();
        textSearch.put("text", Movie.COLLECTION_NAME);
        textSearch.put("search", searchString);
        textSearch.put("filter", filter.getQueryObject());
        return mongoOperations.executeCommand(textSearch);
    }

    private Collection<ObjectId> extractSearchResultIds(CommandResult commandResult) {
        Set<ObjectId> objectIds = new HashSet<ObjectId>();
        BasicDBList resultList = (BasicDBList) commandResult.get("results");
        Iterator<Object> it = resultList.iterator();
        while (it.hasNext()) {
            BasicDBObject resultContainer = (BasicDBObject) it.next();
            BasicDBObject resultObject = (BasicDBObject) resultContainer.get("obj");
            // resultObject now contains a representation of the object we want to retrieve
            ObjectId resultId = (ObjectId) resultObject.get("_id");
            objectIds.add(resultId);
        }
        return objectIds;
    }

    private List<Movie> retrieveSortedAndPaginatedResult(Collection<ObjectId> searchResultIds, Sort sort, long offset, long limit) {
        Criteria criteria = Criteria.where("_id").in(searchResultIds);
        Query query = Query.query(criteria);
        if (offset != 0) {
            query.skip((int) offset);
        }
        if (limit != 0) {
            query.limit((int) limit);
        }
        if (sort != null) {
            query.with(sort);
        }
        return mongoOperations.find(query, clazz);
    }

    private Query mapQuery(MovieQuery query) {
        Query mappedQuery = mapQueryCriteria(query);
        if (query.getTitleFullTextSearch() == null) {
            applySortAndPagination(query, mappedQuery);
        }
        // otherwise ignore sort and pagination, this will be done later as mongo full text search always ranks by relevance
        return mappedQuery;
    }

    private Query mapQueryCriteria(MovieQuery query) {
        Criteria criteria = null;

        if (query.getGenre() != null) {
            criteria = updateCriteria(criteria, "genre", query.getGenre());
        }
        if (query.getYear() != null) {
            criteria = updateCriteria(criteria, "year", query.getYear());
        }
        if (query.isAlreadyWatched() != null) {
            criteria = updateCriteria(criteria, "alreadyWatched", query.isAlreadyWatched());
        }
        if (query.isLikeToWatch() != null) {
            criteria = updateCriteria(criteria, "likeToWatch", query.isLikeToWatch());
        }

        if (criteria == null) {
            // use dummy in that case
            criteria = Criteria.where("title").exists(true);
        }
        return Query.query(criteria);
    }

    private Criteria updateCriteria(Criteria criteria, String key, Object value) {
        if (criteria == null) {
            criteria = Criteria.where(key).is(value);
        }
        else {
            criteria.and(key).is(value);
        }
        return criteria;
    }

    private Query applySortAndPagination(MovieQuery query, Query mappedQuery) {
        if (query.getOffset() != 0) {
            mappedQuery.skip((int) query.getOffset());
        }
        if (query.getLimit() != 0) {
            mappedQuery.limit((int) query.getLimit());
        }
        if (query.getSort() != null) {
            mappedQuery.with(query.getSort());
        }
        return mappedQuery;
    }

}
