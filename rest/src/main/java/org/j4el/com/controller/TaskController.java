package org.j4el.com.controller;

import lombok.RequiredArgsConstructor;
import org.j4el.com.api.TaskApi;
import org.j4el.com.model.CreateTaskDto;
import org.j4el.com.model.TaskResponseDto;
import org.j4el.com.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Validated
@RestController
@RequiredArgsConstructor
public class TaskController implements TaskApi {
    private final TaskService taskService;

    @Override
    public ResponseEntity<Object> createTask(CreateTaskDto createTaskDto) {
        return ResponseEntity.created(URI.create("/task/" + taskService.createTask(createTaskDto).getId())).build();
    }

    @Override
    public ResponseEntity<TaskResponseDto> getTask(Integer pageNumber, Integer pageSize, String groupBy, String sortBy) {
        return ResponseEntity.ok(taskService.findTask(pageNumber, pageSize, groupBy, sortBy));
    }
}
