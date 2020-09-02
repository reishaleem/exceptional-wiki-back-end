package com.exceptionaloutlining.app.models;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskList {

    @Id
    private String id;

    @NotBlank
    private String ownerId; // this could be the ID of many things, universes, wikis, etc..., but it isn't
                            // attached to the User. You get the user from the universe, wiki, or whatever

    private String universeId; // this is the universe to which the tasklist belongs. It may sometimes be the
                               // same as ownerId. it is saved here for cascading changes up to the master
                               // TaskList of the Universe.

    private List<Task> tasks; // this should probably be a PriorityQueue sorted on due date. But for now, just
                              // want to get it working with a list.
}