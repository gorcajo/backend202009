package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.exception.IllegalArgumentsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    private final TimeService timeService;
    private final TaskRepository taskRepository;

    public TaskService(@Autowired TaskRepository taskRepository, @Autowired TimeService timeService) {
        this.taskRepository = taskRepository;
        this.timeService = timeService;
    }

    public List<Task> listTasks() {
        return listTasks(null);
    }

    public List<Task> listTasks(String sortBy) {
        if (Objects.isNull(sortBy)) {
            return taskRepository.findAll();
        } else if (sortBy.equals("createdOn")) {
            return taskRepository.findAllByOrderByCreatedOnAsc();
        } else if (sortBy.equals("priority")) {
            return taskRepository.findAllByOrderByPriorityAsc();
        }

        throw new IllegalArgumentsException("wrong criteria");
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
