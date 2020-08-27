package com.exceptionaloutlining.app.payload.request;

import javax.validation.constraints.NotBlank;

import com.exceptionaloutlining.app.models.User;

import lombok.Data;

@Data
public class CreateUniverseRequest {

    @NotBlank
    private User owner;

    @NotBlank
    private String name;

    private String desc;
}