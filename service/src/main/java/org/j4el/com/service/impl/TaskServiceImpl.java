package org.j4el.com.service.impl;

import org.j4el.com.entity.Task;
import org.j4el.com.exception.TaskException;
import org.j4el.com.mapper.TaskMapper;
import org.j4el.com.model.CreateTaskDto;
import org.j4el.com.repository.TaskRepository;
import org.j4el.com.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.j4el.com.exception.ExceptionMessage.TITLE_ALREADY_EXIST;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    public Task createTask(CreateTaskDto createTaskDto) {
        checkTitleUnique(createTaskDto);
        var task = taskMapper.maptoEntity(createTaskDto);
        return taskRepository.save(task);
    }

    private void findTask() {
        var tasks = taskRepository.findAll(PageRequest.of(0, 10, Sort.by("scheduledDate")))
                .stream().collect(Collectors.groupingBy(Task::getScheduledDate));
    }

    private void checkTitleUnique(CreateTaskDto taskDto) {
        if (taskRepository.existsTaskByTitle(taskDto.getTitle())) {
            throw new TaskException(TITLE_ALREADY_EXIST.name());
        }
    }
}
