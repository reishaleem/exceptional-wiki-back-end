package com.exceptionaloutlining.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "database_sequences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseSequence {
    
    @Id
    private String Id;

    private Long seq;
}