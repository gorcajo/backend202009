package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(@Autowired TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(int id) {
        return taskRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Task createTask(Task taskToBeCreated) {
        return taskRepository.save(taskToBeCreated);
    }

    public void modifyTask(int id, Task task) {
        var storedTask = taskRepository.findById(id).orElseThrow(NotFoundException::new);
        task.setId(storedTask.getId());
        taskRepository.save(task);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
