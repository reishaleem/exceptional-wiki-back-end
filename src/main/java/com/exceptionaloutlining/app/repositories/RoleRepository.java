package com.exceptionaloutlining.app.repositories;

import java.util.Optional;

import com.exceptionaloutlining.app.models.ERole;
import com.exceptionaloutlining.app.models.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}