package com.exceptionaloutlining.app.services;

import java.util.ArrayList;

import com.exceptionaloutlining.app.models.Task;
import com.exceptionaloutlining.app.models.TaskList;
import com.exceptionaloutlining.app.models.Wiki;
import com.exceptionaloutlining.app.payload.response.MessageResponse;
import com.exceptionaloutlining.app.repositories.TaskListRepository;
import com.exceptionaloutlining.app.repositories.WikiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class TaskService {

    private final TaskListRepository taskListRepository;
    private final WikiRepository wikiRepository;

    @Autowired
    public TaskService(TaskListRepository taskListRepository, WikiRepository wikiRepository) {
        this.taskListRepository = taskListRepository;
        this.wikiRepository = wikiRepository;
    }

    public ResponseEntity<?> getWikiTaskList(String wikiId) {

        Wiki wiki = wikiRepository.findById(wikiId)
                .orElseThrow(() -> new RuntimeException("Error: No Wiki found with id: " + wikiId));
        TaskList taskList = taskListRepository.findById(wiki.getTaskListId())
                .orElseThrow(() -> new RuntimeException("Error: No TaskList found with id: " + wiki.getTaskListId()));

        return ResponseEntity.ok(taskList);
    }

    public ResponseEntity<?> createWikiTaskList(String wikiId) {

        Wiki wiki = wikiRepository.findById(wikiId)
                .orElseThrow(() -> new RuntimeException("Error: No Wiki found with id: " + wikiId));
        TaskList taskList = new TaskList();
        taskList.setOwnerId(wikiId);
        taskList.setTasks(new ArrayList<Task>());

        taskListRepository.save(taskList);

        wiki.setTaskListId(taskList.getId());
        wikiRepository.save(wiki);

        return ResponseEntity.ok(new MessageResponse("Task List created successfully", taskList.getId()));
    }
}
