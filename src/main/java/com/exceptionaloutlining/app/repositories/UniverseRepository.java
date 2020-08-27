package com.exceptionaloutlining.app.repositories;

import com.exceptionaloutlining.app.models.Universe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniverseRepository extends MongoRepository<Universe, String> {

}