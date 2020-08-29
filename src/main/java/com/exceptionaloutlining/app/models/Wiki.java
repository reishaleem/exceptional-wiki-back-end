package com.exceptionaloutlining.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wikis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wiki {

    @Transient
    public static final String SEQUENCE_NAME = "wikis_sequence";

    @Id
    private String id;

    private String name;

    private String universeId;

    private String ownerId;

    private String createdTimestamp;

    private String modifiedTimestamp;

    private String body;
}
