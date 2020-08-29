package com.exceptionaloutlining.app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WikiResponse {

    private String id;

    private String name;

    private String universeId;

    private String ownerId;

    private String createdTimestamp;

    private String modifiedTimestamp;

    private String body;
}