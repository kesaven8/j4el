package org.j4el.com.service.impl;

import org.j4el.com.mapper.TaskMapper;
import org.j4el.com.repository.TaskRepository;
import org.j4el.com.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

}
