package com.exceptionaloutlining.app.models;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskList {

    private String id;

    @NotBlank
    private String ownerId; // this could be the ID of many things, universes, wikis, etc...

    private List<Task> tasks; // this should probably be a PriorityQueue sorted on due date. But for now, just want to get it working with a list.
}