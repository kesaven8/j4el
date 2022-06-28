package org.j4el.com.service;


import org.j4el.com.entity.Task;
import org.j4el.com.model.CreateTaskDto;
import org.j4el.com.model.TaskResponseDto;
import org.springframework.data.domain.Page;

public interface TaskService {
    Task createTask(CreateTaskDto createTaskDto);

    TaskResponseDto findTask(Integer pageNumber, Integer pageSize, String groupBy, String sortBy);
}
