package com.example.demo.controller;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TaskDto {

    private final Integer id;
    private final String description;
    private final Boolean completed;
    private final TaskPriority priority;
    private final OffsetDateTime createdOn;
    private final List<SubtaskDto> subtasks;

    public static TaskDto from(Task entity) {
        return new TaskDto(
                entity.getId(),
                entity.getDescription(),
                entity.isCompleted(),
                entity.getPriority(),
                entity.getCreatedOn(),
                entity.getSubtasks().stream().map(SubtaskDto::from).collect(Collectors.toList()));
    }

    public TaskDto() {
        this.id = null;
        this.description = null;
        this.completed = null;
        this.priority = null;
        this.createdOn = null;
        this.subtasks = null;
    }

    public TaskDto(
            Integer id,
            String description,
            Boolean completed,
            TaskPriority priority,
            OffsetDateTime createdOn,
            List<SubtaskDto> subtasks) {

        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdOn = createdOn;
        this.subtasks = subtasks;
    }

    public TaskDto(
            String description,
            Boolean completed,
            TaskPriority priority,
            List<SubtaskDto> subtasks) {

        this.id = null;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdOn = null;
        this.subtasks = subtasks;
    }

    public TaskDto(
            String description,
            Boolean completed,
            TaskPriority priority) {

        this.id = null;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdOn = null;
        this.subtasks = new ArrayList<>();
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

    public List<SubtaskDto> getSubtasks() {
        return subtasks;
    }

    public Task toEntity() {
        if (Objects.isNull(getId())) {
            return new Task(getDescription(), isCompleted(), getPriority(), getCreatedOn());
        } else {
            return new Task(getId(), getDescription(), isCompleted(), getPriority(), getCreatedOn());
        }
    }
}
