package org.j4el.com.service.impl;

import org.j4el.com.entity.Task;
import org.j4el.com.exception.TaskException;
import org.j4el.com.mapper.TaskMapper;
import org.j4el.com.model.CreateTaskDto;
import org.j4el.com.model.TaskResponseDto;
import org.j4el.com.repository.TaskRepository;
import org.j4el.com.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.j4el.com.exception.ExceptionMessage.TITLE_ALREADY_EXIST;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private static final String DEFAULT_GROUP_BY = "scheduledDate";
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

    public TaskResponseDto findTask(Integer pageNumber, Integer pageSize, String groupBy, String sortBy) {
        var pageTasks = taskRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Optional.ofNullable(sortBy).orElse("scheduledDate"))));
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setTaskDto(pageTasks.stream().collect(Collectors.groupingBy(getGroupingClassifier().getOrDefault(groupBy, Task::getScheduledDate))));
        taskResponseDto.setPageNumber(pageTasks.getPageable().getPageNumber());
        taskResponseDto.setTotalPages(pageTasks.getTotalPages());
        taskResponseDto.setPageSize(pageTasks.getPageable().getPageSize());
        taskResponseDto.setTotalElements((int) pageTasks.getTotalElements());
        return taskResponseDto;

    }

    private void checkTitleUnique(CreateTaskDto taskDto) {
        if (taskRepository.existsTaskByTitle(taskDto.getTitle())) {
            throw new TaskException(TITLE_ALREADY_EXIST.name());
        }
    }

    private Map<String, Function<Task, Object>> getGroupingClassifier() {
        //TODO FIX ADD ALL STATIC FIELD
        Map<String, Function<Task, Object>> groupingClassifier = new HashMap<>();
        groupingClassifier.put("scheduledDate", Task::getScheduledDate);
        groupingClassifier.put("location", Task::getLocation);
        groupingClassifier.put("status", Task::getStatus);
        return groupingClassifier;
    }
}
