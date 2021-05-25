package com.example.demo.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    private Boolean completed;
    private TaskPriority priority;
    private OffsetDateTime createdOn;

    @OneToMany(mappedBy = "parentTask", fetch = FetchType.EAGER)
    private List<Subtask> subtasks;

    public Task() {
        this.subtasks = new ArrayList<>();
    }

    public Task(String description, boolean completed, TaskPriority priority, OffsetDateTime createdOn) {
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdOn = createdOn;
        this.subtasks = new ArrayList<>();
    }

    public Task(int id, String description, boolean completed, TaskPriority priority, OffsetDateTime createdOn) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdOn = createdOn;
        this.subtasks = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}
