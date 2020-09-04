package com.exceptionaloutlining.app.payload.response;

import com.exceptionaloutlining.app.models.Task;
import com.exceptionaloutlining.app.models.TaskList;

import lombok.Data;

@Data
public class TaskListResponse {

    private TaskList taskList;
    private Task updatedTask; // this could be a new task or a task that just changed

    public TaskListResponse(TaskList taskList, Task updatedTask) {
        this.taskList = taskList;
        this.updatedTask = updatedTask;
    }

    public TaskListResponse(TaskList taskList) {
        this.taskList = taskList;
    }

}
