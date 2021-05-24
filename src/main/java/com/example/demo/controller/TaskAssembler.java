package com.example.demo.controller;

import com.example.demo.entity.Task;

import java.util.Objects;

public final class TaskAssembler {

    public static TaskDto convert(Task task) {
        return new TaskDto(
                task.getId(),
                task.getDescription(),
                task.isCompleted(),
                task.getPriority(),
                task.getCreatedOn());
    }

    public static Task convert(TaskDto task) {
        if (Objects.isNull(task.getId())) {
            return new Task(
                    task.getDescription(),
                    task.isCompleted(),
                    task.getPriority(),
                    task.getCreatedOn());
        } else {
            return new Task(
                    task.getId(),
                    task.getDescription(),
                    task.isCompleted(),
                    task.getPriority(),
                    task.getCreatedOn());
        }
    }

    private TaskAssembler() {
        throw new RuntimeException();
    }
}
