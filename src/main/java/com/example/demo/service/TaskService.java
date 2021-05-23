package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;

@Service
public class TaskService {
	
	private TaskRepository taskRepository;

	public TaskService(@Autowired TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public List<Task> listTasks() {
		return taskRepository.findAll();
	}

	public Task createTask(Task taskToBeCreated) {
		return taskRepository.save(taskToBeCreated);
	}

	public void deleteTask(int id) {
		taskRepository.deleteById(id);
	}
}
