package org.j4el.com.service;


import org.j4el.com.entity.Task;
import org.j4el.com.model.CreateTaskDto;
import org.springframework.data.domain.Page;

public interface TaskService {
    Task createTask(CreateTaskDto createTaskDto);

    Page<Task> findTask();
}
