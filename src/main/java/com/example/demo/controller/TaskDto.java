package com.example.demo.controller;

import com.example.demo.entity.TaskPriority;

public class TaskDto {

    private final Integer id;
    private final String description;
    private final Boolean completed;
    private final TaskPriority priority;

    public TaskDto() {
        this.id = null;
        this.description = null;
        this.completed = null;
        this.priority = null;
    }

    public TaskDto(Integer id, String description, Boolean completed, TaskPriority priority) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
    }

    public TaskDto(String description, Boolean completed, TaskPriority priority) {
        this.id = null;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public TaskPriority getPriority() {
        return priority;
    }
}
