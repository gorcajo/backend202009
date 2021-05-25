package com.example.demo.controller;

import com.example.demo.entity.Subtask;

import java.util.Objects;

public class SubtaskDto {

    private final Integer id;
    private final String description;
    private final Boolean completed;

    public static SubtaskDto from(Subtask entity) {
        return new SubtaskDto(entity.getId(), entity.getDescription(), entity.isCompleted());
    }

    public SubtaskDto() {
        this.id = null;
        this.description = null;
        this.completed = null;
    }

    public SubtaskDto(Integer id, String description, Boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public SubtaskDto(String description, Boolean completed) {
        this.id = null;
        this.description = description;
        this.completed = completed;
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

    public Subtask toEntity() {
        if (Objects.isNull(getId())) {
            return new Subtask(getDescription(), isCompleted());
        } else {
            return new Subtask(getId(), getDescription(), isCompleted());
        }
    }
}
