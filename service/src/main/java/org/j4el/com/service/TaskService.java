package org.j4el.com.service;


import org.j4el.com.entity.Task;
import org.j4el.com.model.TaskDto;
import org.j4el.com.model.TaskResponseDto;

public interface TaskService {
    Task createTask(TaskDto createTaskDto);

    TaskResponseDto findTask(Integer pageNumber, Integer pageSize, String groupBy, String sortBy);

    Task updateTask(String id, TaskDto taskDto);

    void deleteTask(String id);
}
