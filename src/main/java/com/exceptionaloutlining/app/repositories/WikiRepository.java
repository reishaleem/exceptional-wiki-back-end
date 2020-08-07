package com.exceptionaloutlining.app.repositories;

import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.models.Wiki;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WikiRepository extends MongoRepository<Wiki, String> {

    public List<Wiki> findByOwner(User user);
}
