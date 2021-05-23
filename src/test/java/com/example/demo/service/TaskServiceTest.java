package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository fakeRepo;

    private TaskService service;

    @Before
    public void setup() {
        service = new TaskService(fakeRepo);
    }

    @Test
    public void list_tasks() {
        // arrange

        when(fakeRepo.findAll()).thenReturn(List.of(
                new Task(0, "task0", true, TaskPriority.HIGH),
                new Task(1, "task1", false, TaskPriority.LOW),
                new Task(2, "task2", false, TaskPriority.LOW)
        ));

        // act

        var tasks = service.listTasks();

        // assert

        assertThat(tasks.size(), is(3));
        assertThat(tasks.get(0).getId(), is(0));
        assertThat(tasks.get(0).getDescription(), is("task0"));
        assertThat(tasks.get(0).isCompleted(), is(Boolean.TRUE));
        assertThat(tasks.get(0).getPriority(), is(TaskPriority.HIGH));
        assertThat(tasks.get(2).getId(), is(2));
        assertThat(tasks.get(2).getDescription(), is("task2"));
        assertThat(tasks.get(2).isCompleted(), is(Boolean.FALSE));
        assertThat(tasks.get(2).getPriority(), is(TaskPriority.LOW));
    }

    @Test
    public void create_task() {
        // arrange

        var taskToBeCreated = new Task("task0", false, TaskPriority.LOW);

        when(fakeRepo.save(eq(taskToBeCreated)))
                .thenReturn(new Task(
                        3,
                        taskToBeCreated.getDescription(),
                        taskToBeCreated.isCompleted(),
                        taskToBeCreated.getPriority()
                ));

        // act

        var createdTask = service.createTask(taskToBeCreated);

        // assert

        assertThat(createdTask.getId(), is(3));
        assertThat(createdTask.getDescription(), is("task0"));
        assertThat(createdTask.isCompleted(), is(Boolean.FALSE));
        assertThat(createdTask.getPriority(), is(TaskPriority.LOW));
    }
}
