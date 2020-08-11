package com.exceptionaloutlining.app.services;

import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class UserService {

    private final UserRepository repository;
    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public UserService(UserRepository repository, SequenceGeneratorService sequenceGenerator) {
        this.repository = repository;
        this.sequenceGenerator = sequenceGenerator;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById() {
        return repository.findById("1");
    }

    public boolean deleteUser() {
        try {
            repository.deleteById("1");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User saveNewUser(User user) {
        repository.save(user); // event listener will ensure that the ID is already set before saving rn
        return user;
    }
}
