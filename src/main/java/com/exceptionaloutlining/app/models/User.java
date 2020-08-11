package com.exceptionaloutlining.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Transient
	public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private String id;
    private String username;
    private String password;

    @DBRef
    private List<Wiki> wikis;
}
