package com.exceptionaloutlining.app.services;

import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
