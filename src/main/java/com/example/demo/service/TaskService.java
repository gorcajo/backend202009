package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TimeService timeService;
    private final TaskRepository taskRepository;

    public TaskService(@Autowired TaskRepository taskRepository, @Autowired TimeService timeService) {
        this.taskRepository = taskRepository;
        this.timeService = timeService;
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(int id) {
        return taskRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Task createTask(Task task) {
        task.setCreatedOn(timeService.getCurrentOffsetDateTime());
        return taskRepository.save(task);
    }

    public void modifyTask(int id, Task task) {
        var storedTask = taskRepository.findById(id).orElseThrow(NotFoundException::new);
        task.setId(storedTask.getId());
        taskRepository.save(task);
    }

    public void deleteTask(int id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
        }
    }
}
