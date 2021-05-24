package com.example.demo.controller;

import com.example.demo.entity.TaskPriority;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<TaskDto>> listTasks(
            @RequestParam(value = "completed", required = false) Boolean completed,
            @RequestParam(value = "priority", required = false) TaskPriority priority,
            Pageable pageable) {
        return ResponseEntity.ok(
                taskService
                        .listTasks(pageable, completed, priority)
                        .stream()
                        .map(TaskAssembler::convert)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") int id) {
        return ResponseEntity.ok(TaskAssembler.convert(taskService.getTask(id)));
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto task) {
        return ResponseEntity.ok(TaskAssembler.convert(taskService.createTask(TaskAssembler.convert(task))));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Void> modifyTask(@PathVariable("id") int id, @RequestBody TaskDto task) {
        taskService.modifyTask(id, TaskAssembler.convert(task));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
