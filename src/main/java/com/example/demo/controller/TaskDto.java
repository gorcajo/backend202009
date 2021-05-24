package com.example.demo.controller;

import com.example.demo.entity.TaskPriority;

import java.time.OffsetDateTime;

public class TaskDto {

    private final Integer id;
    private final String description;
    private final Boolean completed;
    private final TaskPriority priority;
    private final OffsetDateTime createdOn;

    public TaskDto() {
        this.id = null;
        this.description = null;
        this.completed = null;
        this.priority = null;
        this.createdOn = null;
    }

    public TaskDto(Integer id, String description, Boolean completed, TaskPriority priority, OffsetDateTime createdOn) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdOn = createdOn;
    }

    public TaskDto(String description, Boolean completed, TaskPriority priority, OffsetDateTime createdOn) {
        this.id = null;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdOn = createdOn;
    }

    public TaskDto(String description, Boolean completed, TaskPriority priority) {
        this.id = null;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdOn = null;
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

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }
}
