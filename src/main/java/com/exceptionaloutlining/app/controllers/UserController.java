package com.exceptionaloutlining.app.controllers;

import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // create a user
    @PostMapping("/register")
    public User createUser(@RequestBody User newUser) {
        return service.saveNewUser(newUser);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // look at specific user profile
    @GetMapping("/profile") // may change endpoint later to something like /account/profile...
    public Optional<User> getLoggedInUser() {
        return service.getUserById(); // will need to edit this later, once we figure out how to get the proper ID...
    }

    @DeleteMapping("/profile") 
    public boolean deleteUser() {
        return service.deleteUser(); // will need to edit this later, once we figure out how to get the proper id...
    }
}
