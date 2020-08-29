package com.exceptionaloutlining.app.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateWikiRequest {

    @NotBlank
    private String ownerId;

    @NotBlank
    private String name;

    private String body; // for now, just going with body
}