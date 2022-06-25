package org.j4el.com.service.impl;

import org.j4el.com.dto.TaskDto;
import org.j4el.com.mapper.TaskMapper;
import org.j4el.com.repository.TaskRepository;
import org.j4el.com.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    public void createTask(TaskDto taskDto) {
        //TODO ONLY TITLE MUST BE UNIQUE
        var task = taskMapper.maptoEntity(taskDto);
        taskRepository.save(task);
    }

    private void checkTitleUnique(TaskDto taskDto) {
        if (taskRepository.existsTaskByTitle(taskDto.getTitle())) {

        }

    }


}
