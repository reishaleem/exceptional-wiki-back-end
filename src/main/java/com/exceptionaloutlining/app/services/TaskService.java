package com.exceptionaloutlining.app.services;

import java.util.ArrayList;
import java.util.List;

import com.exceptionaloutlining.app.models.Task;
import com.exceptionaloutlining.app.models.TaskList;
import com.exceptionaloutlining.app.models.Wiki;
import com.exceptionaloutlining.app.payload.request.CreateTaskRequest;
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
    private SequenceGeneratorService taskSequenceIdGenerator;

    @Autowired
    public TaskService(TaskListRepository taskListRepository, WikiRepository wikiRepository,
            SequenceGeneratorService taskSequenceIdGenerator) {
        this.taskListRepository = taskListRepository;
        this.wikiRepository = wikiRepository;
        this.taskSequenceIdGenerator = taskSequenceIdGenerator;
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

    public ResponseEntity<?> addTask(String id, CreateTaskRequest request) {

        TaskList taskList = taskListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: No TaskList found with id: " + id));
        List<Task> tasks = taskList.getTasks();
        Task task = new Task();
        task.setComplete(false);
        task.setDueCategory("Implement later");
        task.setDueDate(request.getDueDate());
        task.setListId(request.getListId());
        task.setPinned(request.getPinned());
        task.setTask(request.getTask());
        if (task.getId() == null || Long.parseLong(task.getId()) < 1) {
            task.setId(taskSequenceIdGenerator.generateSequence(id + "_sequence"));
        }

        tasks.add(task);

        taskListRepository.save(taskList);

        return ResponseEntity.ok(taskList);
    }
}
