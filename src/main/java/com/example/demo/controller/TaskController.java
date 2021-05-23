package com.example.demo.controller;

import com.example.demo.exception.NotFoundException;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> listTasks() {
        return ResponseEntity.ok(
                taskService
                        .listTasks()
                        .stream()
                        .map(TaskAssembler::convert)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> listTasks(@PathVariable("id") int id) {
        var task = taskService.getTask(id);

        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(TaskAssembler.convert(task.get()));
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto task) {
        return ResponseEntity.ok(TaskAssembler.convert(taskService.createTask(TaskAssembler.convert(task))));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Void> modifyTask(@PathVariable("id") int id, @RequestBody TaskDto task) {
        try {
            taskService.modifyTask(id, TaskAssembler.convert(task));
            return ResponseEntity.noContent().build();
        } catch (NotFoundException nfe) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
