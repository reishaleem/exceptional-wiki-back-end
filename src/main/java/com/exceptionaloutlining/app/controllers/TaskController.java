package com.exceptionaloutlining.app.controllers;

import javax.validation.Valid;

import com.exceptionaloutlining.app.payload.request.CreateTaskRequest;
import com.exceptionaloutlining.app.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // get the tasks of a certain wiki
    @GetMapping("/wikis/{wikiId}")
    public ResponseEntity<?> getWikiTaskList(@PathVariable String wikiId) {
        return taskService.getWikiTaskList(wikiId);
    }

    @GetMapping("/universes/{universeId}")
    public ResponseEntity<?> getUniverseTaskList(@PathVariable String universeId) {
        return taskService.getUniverseTaskList(universeId);
    }

    @PostMapping("/wikis/{wikiId}/create")
    public ResponseEntity<?> createWikiTaskList(@PathVariable String wikiId) {
        return taskService.createWikiTaskList(wikiId);
    }

    @PostMapping("/universes/{universeId}/create")
    public ResponseEntity<?> createUniverseId(@PathVariable String universeId) {
        return taskService.createUniverseTaskList(universeId);
    }

    @PutMapping("/{taskListId}/add")
    public ResponseEntity<?> addTask(@PathVariable String taskListId, @Valid @RequestBody CreateTaskRequest request) {
        return taskService.addTask(taskListId, request);
    }

    @PutMapping("/{taskListId}/{taskId}/toggle_complete")
    public ResponseEntity<?> toggleTaskComplete(@PathVariable String taskListId, @PathVariable String taskId) {
        return taskService.toggleComplete(taskListId, taskId);
    }
}
