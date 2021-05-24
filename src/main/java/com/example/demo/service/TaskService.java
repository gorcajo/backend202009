package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TaskService {

    private final TimeService timeService;
    private final TaskRepository taskRepository;

    public TaskService(@Autowired TaskRepository taskRepository, @Autowired TimeService timeService) {
        this.taskRepository = taskRepository;
        this.timeService = timeService;
    }

    public Page<Task> listTasks(Pageable pageable, Boolean completed, TaskPriority priority) {
        if (!Objects.isNull(completed) && !Objects.isNull(priority)) {
            return taskRepository.findAllByCompletedAndPriority(completed, priority, pageable);
        } else if (!Objects.isNull(completed)) {
            return taskRepository.findAllByCompleted(completed, pageable);
        } else if (!Objects.isNull(priority)) {
            return taskRepository.findAllByPriority(priority, pageable);
        } else {
            return taskRepository.findAll(pageable);
        }
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
