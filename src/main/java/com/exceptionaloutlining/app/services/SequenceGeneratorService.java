package com.exceptionaloutlining.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import com.exceptionaloutlining.app.models.DatabaseSequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * This class generates ID values starting from 1 for the Documents in the
 * database. It is called in the event ModelListener classes.
 * 
 * I'm really not sure if we even need this. It lets IDs start from 1 instead of
 * the long one Mongo gives by default, but not sure it matters.
 */
@Service
public class SequenceGeneratorService {

  private MongoOperations mongoOperations;

  @Autowired
  public SequenceGeneratorService(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  public String generateSequence(String seqName) {
    DatabaseSequence counter = mongoOperations.findAndModify(query(where("__id").is(seqName)),
        new Update().inc("seq", 1), options().returnNew(true).upsert(true), DatabaseSequence.class);
    return !Objects.isNull(counter) ? counter.getSeq().toString() : "1";
  }
}