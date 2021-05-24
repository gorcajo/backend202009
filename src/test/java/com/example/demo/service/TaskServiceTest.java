package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.TaskRepository;
import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository fakeRepo;

    @Mock
    private TimeService fakeTimeService;

    @Captor
    ArgumentCaptor<Task> taskCaptor;

    private TaskService service;

    @Before
    public void setup() {
        service = new TaskService(fakeRepo, fakeTimeService);
    }

    @Test
    public void create_task() {
        // arrange

        var taskToBeCreated = new Task("description", false, TaskPriority.LOW, null);

        var offsetDateTime = OffsetDateTime.of(
                2021,
                5,
                24,
                20,
                16,
                56,
                0,
                ZoneOffset.UTC);

        when(fakeRepo.save(taskToBeCreated))
                .thenReturn(new Task(
                        3,
                        taskToBeCreated.getDescription(),
                        taskToBeCreated.isCompleted(),
                        taskToBeCreated.getPriority(),
                        offsetDateTime));

        when(fakeTimeService.getCurrentOffsetDateTime()).thenReturn(offsetDateTime);

        // act

        var createdTask = service.createTask(taskToBeCreated);

        // assert

        assertThat(createdTask.getId(), is(3));
        assertThat(createdTask.getDescription(), is("description"));
        assertThat(createdTask.isCompleted(), is(Boolean.FALSE));
        assertThat(createdTask.getPriority(), is(TaskPriority.LOW));
        assertThat(createdTask.getCreatedOn().getYear(), is(2021));
        assertThat(createdTask.getCreatedOn().getMonthValue(), is(5));
        assertThat(createdTask.getCreatedOn().getDayOfMonth(), is(24));
        assertThat(createdTask.getCreatedOn().getHour(), is(20));
        assertThat(createdTask.getCreatedOn().getMinute(), is(16));
        assertThat(createdTask.getCreatedOn().getSecond(), is(56));
        assertThat(createdTask.getCreatedOn().getOffset(), is(ZoneOffset.UTC));

        verify(fakeRepo, times(1)).save(taskCaptor.capture());
        assertThat(taskCaptor.getValue().getCreatedOn().getYear(), is(2021));
        assertThat(taskCaptor.getValue().getCreatedOn().getMonthValue(), is(5));
        assertThat(taskCaptor.getValue().getCreatedOn().getDayOfMonth(), is(24));
        assertThat(taskCaptor.getValue().getCreatedOn().getHour(), is(20));
        assertThat(taskCaptor.getValue().getCreatedOn().getMinute(), is(16));
        assertThat(taskCaptor.getValue().getCreatedOn().getSecond(), is(56));
        assertThat(taskCaptor.getValue().getCreatedOn().getOffset(), is(ZoneOffset.UTC));

    }

    @Test
    public void list_tasks() {
        // arrange

        when(fakeRepo.findAll()).thenReturn(List.of(
                new Task(0, "description-0", true, TaskPriority.HIGH, OffsetDateTime.now()),
                new Task(1, "description-1", false, TaskPriority.LOW, OffsetDateTime.now()),
                new Task(2, "description-2", false, TaskPriority.LOW, OffsetDateTime.now())
        ));

        // act

        var tasks = service.listTasks();

        // assert

        assertThat(tasks.size(), is(3));
        assertThat(tasks.get(0).getId(), is(0));
        assertThat(tasks.get(0).getDescription(), is("description-0"));
        assertThat(tasks.get(0).isCompleted(), is(Boolean.TRUE));
        assertThat(tasks.get(0).getPriority(), is(TaskPriority.HIGH));
        assertThat(tasks.get(2).getId(), is(2));
        assertThat(tasks.get(2).getDescription(), is("description-2"));
        assertThat(tasks.get(2).isCompleted(), is(Boolean.FALSE));
        assertThat(tasks.get(2).getPriority(), is(TaskPriority.LOW));
    }

    @Test
    public void list_tasks_order_by_priority() {
        // arrange

        when(fakeRepo.findAllByOrderByPriorityAsc()).thenReturn(List.of(
                new Task(1, "description-1", false, TaskPriority.LOW, OffsetDateTime.now()),
                new Task(2, "description-2", false, TaskPriority.MEDIUM, OffsetDateTime.now()),
                new Task(0, "description-0", true, TaskPriority.HIGH, OffsetDateTime.now())
        ));

        // act

        var tasks = service.listTasks("priority");

        // assert

        assertThat(tasks.size(), is(3));
        assertThat(tasks.get(0).getId(), is(1));
        assertThat(tasks.get(0).getDescription(), is("description-1"));
        assertThat(tasks.get(0).isCompleted(), is(Boolean.FALSE));
        assertThat(tasks.get(0).getPriority(), is(TaskPriority.LOW));
        assertThat(tasks.get(2).getId(), is(0));
        assertThat(tasks.get(2).getDescription(), is("description-0"));
        assertThat(tasks.get(2).isCompleted(), is(Boolean.TRUE));
        assertThat(tasks.get(2).getPriority(), is(TaskPriority.HIGH));
    }

    @Test
    public void list_tasks_order_by_created_on() {
        // arrange

        when(fakeRepo.findAllByOrderByCreatedOnAsc()).thenReturn(List.of(
                new Task(2, "description-2", false, TaskPriority.MEDIUM, OffsetDateTime.now()),
                new Task(0, "description-0", true, TaskPriority.HIGH, OffsetDateTime.now()),
                new Task(1, "description-1", false, TaskPriority.LOW, OffsetDateTime.now())
        ));

        // act

        var tasks = service.listTasks("createdOn");

        // assert

        assertThat(tasks.size(), is(3));
        assertThat(tasks.get(0).getId(), is(2));
        assertThat(tasks.get(0).getDescription(), is("description-2"));
        assertThat(tasks.get(0).isCompleted(), is(Boolean.FALSE));
        assertThat(tasks.get(0).getPriority(), is(TaskPriority.MEDIUM));
        assertThat(tasks.get(2).getId(), is(1));
        assertThat(tasks.get(2).getDescription(), is("description-1"));
        assertThat(tasks.get(2).isCompleted(), is(Boolean.FALSE));
        assertThat(tasks.get(2).getPriority(), is(TaskPriority.LOW));
    }

    @Test
    public void get_task() {
        // arrange

        when(fakeRepo.findById(5))
                .thenReturn(Optional.of(new Task(
                        5,
                        "description",
                        false,
                        TaskPriority.LOW,
                        OffsetDateTime.now())));

        // act

        var task = service.getTask(5);

        // assert

        assertThat(task.getId(), is(5));
        assertThat(task.getDescription(), is("description"));
        assertThat(task.isCompleted(), is(Boolean.FALSE));
        assertThat(task.getPriority(), is(TaskPriority.LOW));
    }

    @Test(expected = NotFoundException.class)
    public void get_inexistent_task() {
        // arrange

        when(fakeRepo.findById(8)).thenReturn(Optional.empty());

        // act

        var task = service.getTask(8);

        // assert
    }

    @Test
    public void modify_task() {
        // arrange

        when(fakeRepo.findById(4))
                .thenReturn(Optional.of(new Task(
                        4,
                        "original-description",
                        true,
                        TaskPriority.MEDIUM,
                        OffsetDateTime.now())));

        // act

        service.modifyTask(
                4,
                new Task("new-description", true, TaskPriority.MEDIUM, OffsetDateTime.now()));

        // assert

        verify(fakeRepo, times(1)).findById(4);
        verify(fakeRepo, times(1)).save(taskCaptor.capture());
        assertThat(taskCaptor.getValue().getDescription(), is("new-description"));
        assertThat(taskCaptor.getValue().isCompleted(), is(Boolean.TRUE));
        assertThat(taskCaptor.getValue().getPriority(), is(TaskPriority.MEDIUM));
    }

    @Test(expected = NotFoundException.class)
    public void modify_inexistent_task() {
        // arrange

        // act

        service.modifyTask(4, new Task("new-description", true, TaskPriority.MEDIUM, OffsetDateTime.now()));

        // assert
    }

    @Test
    public void delete_task() {
        // arrange

        when(fakeRepo.findById(3)).thenReturn(Optional.of(new Task()));

        // act

        service.deleteTask(3);

        // assert

        verify(fakeRepo, times(1)).deleteById(3);
    }

    @Test
    public void delete_inexistent_task() {
        // arrange

        when(fakeRepo.findById(3)).thenReturn(Optional.empty());

        // act

        service.deleteTask(3);

        // assert

        verify(fakeRepo, times(0)).deleteById(anyInt());
    }
}
