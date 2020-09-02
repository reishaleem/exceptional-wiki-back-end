package com.exceptionaloutlining.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private String id; // not totally sure we need this.
    private String listId; // the list in which the task belongs
    private String task;
    private String dueDate;
    private String dueCategory;
    private Boolean pinned;
    private Boolean complete;
}