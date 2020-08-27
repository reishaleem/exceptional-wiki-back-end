package com.exceptionaloutlining.app.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 255)
    private String bio;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 120)
    private String password;
}