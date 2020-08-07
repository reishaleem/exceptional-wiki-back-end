package com.exceptionaloutlining.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wiki {

    @Id
    private String id;

    @DBRef
    private User owner;

    private String dateCreated;
    private String lastEdited;
    private String body;
}
