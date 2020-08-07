package com.exceptionaloutlining.app.controllers;

import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // create a user
    @PostMapping("/register")
    public User createUser(@RequestBody User newUser) {
        return service.saveNewUser(newUser);
    }
}
