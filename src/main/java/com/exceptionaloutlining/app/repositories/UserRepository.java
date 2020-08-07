package com.exceptionaloutlining.app.repositories;

import com.exceptionaloutlining.app.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findByUsername(String username);
}
