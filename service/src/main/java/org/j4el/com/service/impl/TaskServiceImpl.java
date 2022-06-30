package org.j4el.com.service.impl;

import org.j4el.com.entity.Task;
import org.j4el.com.exception.TaskException;
import org.j4el.com.mapper.TaskMapper;
import org.j4el.com.model.TaskDto;
import org.j4el.com.model.TaskResponseDto;
import org.j4el.com.repository.TaskRepository;
import org.j4el.com.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.j4el.com.exception.ExceptionMessage.*;

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

    public Task createTask(TaskDto taskDto) {
        checkScheduleDateIsAfter(taskDto);
        checkTitleUnique(taskDto);
        var task = taskMapper.maptoEntity(taskDto);
        return taskRepository.save(task);
    }

    public TaskResponseDto findTask(Integer pageNumber, Integer pageSize, String groupBy, String sortBy) {
        var pageTasks = taskRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Optional.ofNullable(sortBy).orElse(DEFAULT_GROUP_BY))));
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setTaskDto(pageTasks.stream().map(taskMapper::mapToDto)
                .collect(Collectors.groupingBy(getGroupingClassifier().getOrDefault(groupBy, TaskDto::getScheduledDate))));
        taskResponseDto.setPageNumber(pageTasks.getPageable().getPageNumber());
        taskResponseDto.setTotalPages(pageTasks.getTotalPages());
        taskResponseDto.setPageSize(pageTasks.getPageable().getPageSize());
        taskResponseDto.setTotalElements((int) pageTasks.getTotalElements());
        return taskResponseDto;

    }

    private void checkTitleUnique(TaskDto taskDto) {
        if (taskRepository.existsTaskByTitle(taskDto.getTitle())) {
            throw new TaskException(TITLE_ALREADY_EXIST.name());
        }
    }

    private void checkScheduleDateIsAfter(TaskDto taskDto) {
        if (taskDto.getScheduledDate().isBefore(LocalDate.now()) || !LocalDate.now().isEqual(taskDto.getScheduledDate())) {
            throw new TaskException(TASK_IN_PAST.name());
        }
    }

    private Map<String, Function<TaskDto, Object>> getGroupingClassifier() {
        //TODO FIX ADD ALL STATIC FIELD
        Map<String, Function<TaskDto, Object>> groupingClassifier = new HashMap<>();
        groupingClassifier.put("scheduledDate", TaskDto::getScheduledDate);
        groupingClassifier.put("location", TaskDto::getLocation);
        groupingClassifier.put("status", TaskDto::getStatus);
        return groupingClassifier;
    }

    @Override
    public Task updateTask(String id, TaskDto taskDto) {
        var task = taskRepository.findById(Long.parseLong(id)).orElseThrow(() -> new TaskException(ID_NOT_FOUND.name()));

        if (Task.Status.COMPLETED.equals(task.getStatus())) {
            throw new TaskException(CANNOT_UPDATE_TASK.name());
        } else {
            if (task.getScheduledDate().toLocalDate().isBefore(LocalDate.now())) {
                throw new TaskException(CANNOT_UPDATE_TASK.name());
            }
        }
        if (!task.getTitle().equals(taskDto.getTitle()) && taskRepository.existsTaskByTitle(taskDto.getTitle())) {
            throw new TaskException(TITLE_ALREADY_EXIST.name());
        }

        task.setTitle(taskDto.getTitle());
        task.setLocation(taskDto.getLocation());
        task.setDescription(taskDto.getDescription());
        task.setUpdateOn(LocalDateTime.now());
        task.setStatus(Task.Status.valueOf(taskDto.getStatus()));
        task.setScheduledDate(LocalDateTime.of(taskDto.getScheduledDate(), LocalTime.now()));
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String id) {
        var task = taskRepository.findById(Long.parseLong(id)).orElseThrow(() -> new TaskException(ID_NOT_FOUND.name()));
        taskRepository.delete(task);
    }
}
