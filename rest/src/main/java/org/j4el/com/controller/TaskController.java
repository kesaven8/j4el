package org.j4el.com.controller;

import lombok.RequiredArgsConstructor;
import org.j4el.com.api.TaskApi;
import org.j4el.com.entity.Task;
import org.j4el.com.exception.TaskException;
import org.j4el.com.model.TaskDto;
import org.j4el.com.model.TaskResponseDto;
import org.j4el.com.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.j4el.com.exception.ExceptionMessage.*;

@Validated
@RestController
@RequiredArgsConstructor
public class TaskController implements TaskApi {
    private final TaskService taskService;

    @Override
    public ResponseEntity<Object> createTask(TaskDto taskDto) {
        return ResponseEntity.created(URI.create("/task/" + taskService.createTask(taskDto).getId())).build();
    }

    @Override
    public ResponseEntity<TaskResponseDto> getTask(Integer pageNumber, Integer pageSize, String groupBy, String sortBy) {
        return ResponseEntity.ok(taskService.findTask(pageNumber, pageSize, groupBy, sortBy));
    }

    @Override
    public ResponseEntity<Object> updateTask(String id, TaskDto taskDto) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new TaskException(INVALID_ID.name());
        }
        validateFields(taskDto);
       var updatedTask= taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    private void validateFields(TaskDto taskDto) {

        if (taskDto.getTitle() == null) {
            throw new TaskException(EMPTY_TITLE.name());
        }
        if (taskDto.getLocation() == null) {
            throw new TaskException(EMPTY_LOCATION.name());
        }
        if (taskDto.getScheduledDate() == null) {
            throw new TaskException(SCHEDULED_DATE_NULL.name());
        }
        if (!Task.Status.COMPLETED.name().equals(taskDto.getStatus()) && !Task.Status.NOT_COMPLETED.name().equals(taskDto.getStatus())) {
            throw new TaskException(INVALID_TASK_STATUS.name());
        }
    }

    @Override
    public ResponseEntity<Object> deleteTask(String id) {
        try {
             Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new TaskException(INVALID_ID.name());
        }
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
