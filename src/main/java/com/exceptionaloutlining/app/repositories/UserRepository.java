package com.exceptionaloutlining.app.repositories;

import java.util.Optional;

import com.exceptionaloutlining.app.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    public Optional<User> findByUsername(String username);

    public Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
