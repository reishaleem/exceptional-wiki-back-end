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
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // create a user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return service.saveNewUser(signUpRequest);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return service.authenticateUser(loginRequest);
    }

    // look at specific user profile
    @GetMapping("/{id}/details")
    public User getUserDetails(@PathVariable String id) {
        System.out.println("Getting deatils...");
        return service.getUserById(id);
    }

    // should probably update so that the id is being sent in params, right??
    @PutMapping("/{id}/update_profile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UpdateUserProfileRequest updateRequest,
            @PathVariable String id) {
        return service.updateUserProfile(updateRequest, id);
    }

    // should probably update so that the id is being sent in params, right??
    @PutMapping("/{id}/update_security")
    public ResponseEntity<?> updateUserSecurity(@Valid @RequestBody UpdateUserSecurityRequest updateRequest,
            @PathVariable String id) {
        return service.updateUserSecurity(updateRequest, id);
    }

    // should probably update so that the id is being sent in params, right??
    @DeleteMapping("/{id}/delete_user")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return service.deleteUser(id);
    }

    @GetMapping("/{id}/universes")
    public ResponseEntity<?> getUserUniverseList(@PathVariable String id) {
        return service.getUniverseList(id);
    }
}
