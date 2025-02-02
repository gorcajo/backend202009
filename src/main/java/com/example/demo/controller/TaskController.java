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
                        .map(TaskDto::from)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") int id) {
        return ResponseEntity.ok(TaskDto.from(taskService.getTask(id)));
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto task) {
        return ResponseEntity.ok(TaskDto.from(taskService.createTask(task.toEntity())));
    }

    @PostMapping("/tasks/{id}/subtasks")
    public ResponseEntity<TaskDto> addSubtask(@PathVariable("id") int id, @RequestBody SubtaskDto subtask) {
        return ResponseEntity.ok(TaskDto.from(taskService.addSubtask(id, subtask.toEntity())));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Void> modifyTask(@PathVariable("id") int id, @RequestBody TaskDto task) {
        taskService.modifyTask(id, task.toEntity());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
