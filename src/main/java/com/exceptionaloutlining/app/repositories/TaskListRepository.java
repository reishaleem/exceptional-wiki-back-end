package com.exceptionaloutlining.app.repositories;

import com.exceptionaloutlining.app.models.TaskList;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskListRepository extends MongoRepository<TaskList, String> {

}
