package com.exceptionaloutlining.app.controllers;

import com.exceptionaloutlining.app.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // get the tasks of a certain wiki in a certain universe
    @GetMapping("/wikis/{wikiId}")
    public ResponseEntity<?> getWikiTaskList(@PathVariable String wikiId) {
        return taskService.getWikiTaskList(wikiId);
    }

    @PostMapping("/tasks/wikis/{wikiId}/create")
    public ResponseEntity<?> createWikiTaskList(@PathVariable String wikiId) {
        return taskService.createWikiTaskList(wikiId);
    }
}
