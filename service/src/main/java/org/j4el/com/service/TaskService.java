package org.j4el.com.service;


import org.j4el.com.entity.Task;
import org.j4el.com.model.CreateTaskDto;

public interface TaskService {
    Task createTask(CreateTaskDto createTaskDto);
}
