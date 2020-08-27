package com.exceptionaloutlining.app.controllers;

import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.payload.request.LoginRequest;
import com.exceptionaloutlining.app.payload.request.SignUpRequest;
import com.exceptionaloutlining.app.payload.request.UpdateUserProfileRequest;
import com.exceptionaloutlining.app.payload.request.UpdateUserSecurityRequest;
import com.exceptionaloutlining.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // just for testing purposes
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // look at specific user details
    @GetMapping("/{id}/details")
    public User getUserDetails(@PathVariable String id) {
        return service.getUserById(id);
    }

    // get a User's Universe List
    @GetMapping("/{id}/universes")
    public ResponseEntity<?> getUserUniverseList(@PathVariable String id) {
        return service.getUniverseList(id);
    }

    // create a user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return service.saveNewUser(signUpRequest);
    }

    // authenticate a user (a.k.a. create an auth token)
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return service.authenticateUser(loginRequest);
    }

    // update a user's name, email, username, and/or bio
    @PutMapping("/{id}/update_profile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UpdateUserProfileRequest updateRequest,
            @PathVariable String id) {
        return service.updateUserProfile(updateRequest, id);
    }

    // update a user's current password
    @PutMapping("/{id}/update_security")
    public ResponseEntity<?> updateUserSecurity(@Valid @RequestBody UpdateUserSecurityRequest updateRequest,
            @PathVariable String id) {
        return service.updateUserSecurity(updateRequest, id);
    }

    // delete a user's entire account
    @DeleteMapping("/{id}/delete_user")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return service.deleteUser(id);
    }

}
