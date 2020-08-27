package com.exceptionaloutlining.app.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class UpdateUserProfileRequest {

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
}