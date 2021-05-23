package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    private Boolean completed;
    private TaskPriority priority;

    public Task() {

    }

    public Task(String description, boolean completed, TaskPriority priority) {
        this.description = description;
        this.completed = completed;
        this.priority = priority;
    }

    public Task(int id, String description, boolean completed, TaskPriority priority) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
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
}
