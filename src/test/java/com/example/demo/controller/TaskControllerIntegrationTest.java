package com.example.demo.controller;

import com.example.demo.entity.TaskPriority;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private List<TaskDto> initialTasks;

    @BeforeEach
    public void setup() {
        var tasksToBeCreated = List.of(
                new TaskDto(
                        "task0",
                        false,
                        TaskPriority.MEDIUM),
                new TaskDto(
                        "task1",
                        false,
                        TaskPriority.LOW),
                new TaskDto(
                        "task2",
                        false,
                        TaskPriority.HIGH)
        );

        initialTasks = tasksToBeCreated
                .stream()
                .map(task -> given()
                        .baseUri("http://localhost:" + port)
                        .contentType(ContentType.JSON)
                        .body(task)
                        .when()
                        .post("/v1/tasks")
                        .then()
                        .assertThat()
                        .statusCode(HTTP_OK)
                        .extract()
                        .as(TaskDto.class))
                .collect(Collectors.toList());
    }

    @Test
    void list_tasks() {
        given()
                .baseUri("http://localhost:" + port)
                .when()
                .get("/v1/tasks")
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TaskDto[].class);
    }

    @Test
    void list_tasks_order_by_created_on() {
        given()
                .baseUri("http://localhost:" + port)
                .when()
                .get("/v1/tasks?orderBy=createdOn")
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TaskDto[].class);
    }

    @Test
    void list_tasks_order_by_priority() {
        var tasks = given()
                .baseUri("http://localhost:" + port)
                .when()
                .get("/v1/tasks?orderBy=priority")
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TaskDto[].class);

        assertThat(tasks[0].getPriority(), is(TaskPriority.LOW));
        assertThat(tasks[1].getPriority(), is(TaskPriority.MEDIUM));
        assertThat(tasks[2].getPriority(), is(TaskPriority.HIGH));
    }

    @Test
    void list_tasks_order_by_wrong_field() {
        var message = given()
                .baseUri("http://localhost:" + port)
                .when()
                .get("/v1/tasks?orderBy=wrong")
                .then()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .asString();

        assertThat(message, is("wrong criteria"));
    }

    @Test
    void get_task() {
        given()
                .baseUri("http://localhost:" + port)
                .when()
                .get("/v1/tasks/" + initialTasks.get(0).getId())
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TaskDto.class);
    }

    @Test
    void get_inexistent_task() {
        given()
                .baseUri("http://localhost:" + port)
                .when()
                .get("/v1/tasks/-1")
                .then()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    void modify_task() {
        given()
                .baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .body(new TaskDto("new-description", true, TaskPriority.MEDIUM))
                .when()
                .put("/v1/tasks/" + initialTasks.get(0).getId())
                .then()
                .assertThat()
                .statusCode(HTTP_NO_CONTENT);
    }

    @Test
    void modify_inexistent_task() {
        given()
                .baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .body(new TaskDto("new-description", true, TaskPriority.MEDIUM))
                .when()
                .put("/v1/tasks/-1")
                .then()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    void delete_inexistent_task() {
        given()
                .baseUri("http://localhost:" + port)
                .when()
                .delete("/v1/tasks/-1")
                .then()
                .assertThat()
                .statusCode(HTTP_NO_CONTENT);
    }

    @AfterEach
    public void cleanup() {
        for (TaskDto task : initialTasks) {
            given()
                    .baseUri("http://localhost:" + port)
                    .when()
                    .delete("/v1/tasks/" + task.getId())
                    .then()
                    .assertThat()
                    .statusCode(HTTP_NO_CONTENT);
        }
    }
}