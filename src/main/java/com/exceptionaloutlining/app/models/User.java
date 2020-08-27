package com.exceptionaloutlining.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String name;

    @Size(max = 255)
    private String bio;

    @JsonIgnore // don't think we need to be passing password to front end ever
    @NotBlank
    @Size(min = 6, max = 120)
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @DBRef
    private List<Wiki> wikis;

    @DBRef
    private List<Universe> universes;
    // add custom templates they created (put here so that they can use it in any
    // universe)
    // add sheets as well, same logic as templates.

    public User(String username, String email, String name, String password, String bio) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.bio = bio;
    }
}
