package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(@Autowired TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTask(int id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task taskToBeCreated) {
        return taskRepository.save(taskToBeCreated);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
