package com.exceptionaloutlining.app.models;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "universes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Universe {

    @Transient
    public static final String SEQUENCE_NAME = "universes_sequence";

    @Id
    private String id;

    // rather than using DBRef, just manually referencing by keeping ID values,
    // making second queries to each collection as needed.
    private String ownerId;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 500)
    private String description;

    private String createdTimestamp;

    private String modifiedTimestamp;

    private List<String> wikis;

    private String taskListId;

    @DBRef
    private List<Map> maps;

    @DBRef
    private List<Timeline> timelines;

    @DBRef
    private List<Calendar> calendars;

    @DBRef
    private List<Chart> charts;

    @DBRef
    private Notebook notebook;

}