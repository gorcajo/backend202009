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

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private List<TaskDto> initialTasks;

    @BeforeEach
    public void setup() {
        initialTasks = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            initialTasks.add(given()
                    .baseUri("http://localhost:" + port)
                    .contentType(ContentType.JSON)
                    .body(new TaskDto("task" + i, false, TaskPriority.LOW))
                    .when()
                    .post("/v1/tasks")
                    .then()
                    .assertThat()
                    .statusCode(HTTP_OK)
                    .extract()
                    .as(TaskDto.class));
        }
    }

    @Test
    void list_tasks() {
        given()
                .baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/tasks")
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TaskDto[].class);
    }

    @AfterEach
    public void cleanup() {
        for (TaskDto task : initialTasks) {
            given()
                    .baseUri("http://localhost:" + port)
                    .contentType(ContentType.JSON)
                    .body(new TaskDto("task0", false, TaskPriority.LOW))
                    .when()
                    .delete("/v1/tasks/" + task.getId())
                    .then()
                    .assertThat()
                    .statusCode(HTTP_NO_CONTENT);
        }
    }
}