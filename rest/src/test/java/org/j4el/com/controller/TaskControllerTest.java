package org.j4el.com.controller;

import org.assertj.core.api.Assertions;
import org.j4el.com.model.TaskDto;
import org.j4el.com.model.TaskResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {
    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void saveTaskTest() {
        TaskDto taskDto = new TaskDto();
        taskDto.setScheduledDate(LocalDate.now());
        taskDto.setDescription("This is a description");
        taskDto.setLocation("Location");
        taskDto.setStatus("COMPLETED");
        taskDto.setTitle("Title");
        var result = restTemplate.postForEntity("http://localhost:" + port + "/task/create", taskDto, Object.class);
        Assertions.assertThat(result.getStatusCode().is2xxSuccessful()).isEqualTo(true);
    }

    @Test
    public void saveTaskDateInThePastTest() {
        TaskDto taskDto = new TaskDto();
        taskDto.setScheduledDate(LocalDate.now().minusDays(1L));
        taskDto.setDescription("This is a description");
        taskDto.setLocation("Location");
        taskDto.setStatus("COMPLETED");
        taskDto.setTitle("Title");
        var result = restTemplate.postForEntity("http://localhost:" + port + "/task/create", taskDto, String.class);
        Assertions.assertThat(result.getStatusCode().isError()).isEqualTo(true);
    }

    @Test
    public void getTask() {
        var reponse = restTemplate.getForEntity("http://localhost:" + port + "/task?pageNumber=0&pageSize=10", TaskResponseDto.class);
        var body = reponse.getBody();
    }
}
