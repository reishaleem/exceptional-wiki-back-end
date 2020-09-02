package com.exceptionaloutlining.app.services;

import java.util.ArrayList;
import java.util.List;

import com.exceptionaloutlining.app.models.Task;
import com.exceptionaloutlining.app.models.TaskList;
import com.exceptionaloutlining.app.models.Universe;
import com.exceptionaloutlining.app.models.Wiki;
import com.exceptionaloutlining.app.payload.request.CreateTaskRequest;
import com.exceptionaloutlining.app.payload.response.MessageResponse;
import com.exceptionaloutlining.app.repositories.TaskListRepository;
import com.exceptionaloutlining.app.repositories.UniverseRepository;
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
        private final UniverseRepository universeRepository;
        private SequenceGeneratorService taskSequenceIdGenerator;

        @Autowired
        public TaskService(TaskListRepository taskListRepository, WikiRepository wikiRepository,
                        UniverseRepository universeRepository, SequenceGeneratorService taskSequenceIdGenerator) {
                this.taskListRepository = taskListRepository;
                this.wikiRepository = wikiRepository;
                this.universeRepository = universeRepository;
                this.taskSequenceIdGenerator = taskSequenceIdGenerator;
        }

        public ResponseEntity<?> getWikiTaskList(String wikiId) {

                Wiki wiki = wikiRepository.findById(wikiId)
                                .orElseThrow(() -> new RuntimeException("Error: No Wiki found with id: " + wikiId));
                TaskList taskList = taskListRepository.findById(wiki.getTaskListId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Error: No TaskList found with id: " + wiki.getTaskListId()));

                return ResponseEntity.ok(taskList);
        }

        public ResponseEntity<?> getUniverseTaskList(String universeId) {

                Universe universe = universeRepository.findById(universeId).orElseThrow(
                                () -> new RuntimeException("Error: No Universe found with id: " + universeId));
                TaskList taskList = taskListRepository.findById(universe.getTaskListId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Error: No TaskList found with id: " + universe.getTaskListId()));

                return ResponseEntity.ok(taskList);
        }

        public ResponseEntity<?> createWikiTaskList(String wikiId) {

                Wiki wiki = wikiRepository.findById(wikiId)
                                .orElseThrow(() -> new RuntimeException("Error: No Wiki found with id: " + wikiId));
                TaskList taskList = new TaskList();
                taskList.setOwnerId(wikiId);
                taskList.setUniverseId(wiki.getUniverseId());
                taskList.setTasks(new ArrayList<Task>());

                taskListRepository.save(taskList);

                wiki.setTaskListId(taskList.getId());
                wikiRepository.save(wiki);

                return ResponseEntity.ok(new MessageResponse("Task List created successfully", taskList.getId()));
        }

        public ResponseEntity<?> createUniverseTaskList(String universeId) {

                Universe universe = universeRepository.findById(universeId).orElseThrow(
                                () -> new RuntimeException("Error: No Universe found with id: " + universeId));
                TaskList taskList = new TaskList();
                taskList.setOwnerId(universeId);
                taskList.setUniverseId(universe.getId());
                taskList.setTasks(new ArrayList<Task>());

                taskListRepository.save(taskList);

                universe.setTaskListId(taskList.getId());
                universeRepository.save(universe);

                return ResponseEntity.ok(new MessageResponse("Task List created successfully", taskList.getId()));
        }

        public ResponseEntity<?> addTask(String id, CreateTaskRequest request) {

                TaskList taskList = taskListRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Error: No TaskList found with id: " + id));
                List<Task> tasks = taskList.getTasks();

                // need this so we can cascade the save up to the Universe that has this. Then
                // we can avoid a bunch of GET requests, maybe.
                Universe universe = universeRepository.findById(taskList.getUniverseId()).orElseThrow(
                                () -> new RuntimeException("Error: No universe with id: " + taskList.getUniverseId()));
                TaskList headTaskList = taskListRepository.findById(universe.getTaskListId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Error: No TaskList found with id: " + universe.getTaskListId()));

                Task task = new Task();
                task.setComplete(false);
                task.setDueCategory("Implement later");
                task.setDueDate(request.getDueDate());
                task.setListId(id);
                task.setPinned(request.getPinned());
                task.setTask(request.getTask());
                if (task.getId() == null || Long.parseLong(task.getId()) < 1) {
                        task.setId(taskSequenceIdGenerator.generateSequence(id + "_sequence"));
                }

                tasks.add(task);
                headTaskList.getTasks().add(task);

                taskListRepository.save(taskList);
                taskListRepository.save(headTaskList);

                return ResponseEntity.ok(taskList);
        }
}
