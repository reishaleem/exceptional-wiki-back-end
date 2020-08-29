package com.exceptionaloutlining.app.repositories;

import com.exceptionaloutlining.app.models.Wiki;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WikiRepository extends MongoRepository<Wiki, String> {

}
