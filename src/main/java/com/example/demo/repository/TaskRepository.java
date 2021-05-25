package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {

    Page<Task> findAllByPriority(TaskPriority priority, Pageable pageable);

    Page<Task> findAllByCompleted(boolean completed, Pageable pageable);

    Page<Task> findAllByCompletedAndPriority(boolean completed, TaskPriority priority, Pageable pageable);
}
