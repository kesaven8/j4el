package org.j4el.com.controller;

import lombok.RequiredArgsConstructor;
import org.j4el.com.api.TaskApi;
import org.j4el.com.entity.Task;
import org.j4el.com.model.CreateTaskDto;
import org.j4el.com.model.TaskDto;
import org.j4el.com.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<List<TaskDto>> getTask(String pageNumber, String pageSize, String groupBy, String sortBy) {
        return TaskApi.super.getTask(pageNumber, pageSize, groupBy, sortBy);
    }

    @GetMapping("/get")
    public Page<Task> getPage() {
        return taskService.findTask();
    }
}
