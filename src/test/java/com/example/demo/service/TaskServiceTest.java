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
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

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
    public void get_task() {
        // arrange

        when(fakeRepo.findById(5))
                .thenReturn(Optional.of(new Task(
                        5,
                        "task",
                        false,
                        TaskPriority.LOW)));

        // act

        var task = service.getTask(5);

        // assert

        assertThat(task.isPresent(), is(Boolean.TRUE));
        assertThat(task.get().getId(), is(5));
        assertThat(task.get().getDescription(), is("task"));
        assertThat(task.get().isCompleted(), is(Boolean.FALSE));
        assertThat(task.get().getPriority(), is(TaskPriority.LOW));
    }

    @Test
    public void get_inexistent_task() {
        // arrange

        when(fakeRepo.findById(8)).thenReturn(Optional.empty());

        // act

        var task = service.getTask(8);

        // assert

        assertThat(task.isPresent(), is(Boolean.FALSE));
    }

    @Test
    public void create_task() {
        // arrange

        var taskToBeCreated = new Task("task0", false, TaskPriority.LOW);

        when(fakeRepo.save(taskToBeCreated))
                .thenReturn(new Task(
                        3,
                        taskToBeCreated.getDescription(),
                        taskToBeCreated.isCompleted(),
                        taskToBeCreated.getPriority()));

        // act

        var createdTask = service.createTask(taskToBeCreated);

        // assert

        assertThat(createdTask.getId(), is(3));
        assertThat(createdTask.getDescription(), is("task0"));
        assertThat(createdTask.isCompleted(), is(Boolean.FALSE));
        assertThat(createdTask.getPriority(), is(TaskPriority.LOW));
    }

    @Test
    public void delete_task() {
        // arrange

        // act

        service.deleteTask(3);

        // assert

        verify(fakeRepo, times(1)).deleteById(3);
    }
}
