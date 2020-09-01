package com.exceptionaloutlining.app.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateTaskRequest {

    @NotBlank
    private String listId; // the list in which the task belongs

    @NotBlank
    private String task;

    private String dueDate;

    private Boolean pinned;

}
