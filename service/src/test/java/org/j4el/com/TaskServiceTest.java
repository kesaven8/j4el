package org.j4el.com;

import org.j4el.com.entity.Task;
import org.j4el.com.exception.TaskException;
import org.j4el.com.mapper.TaskMapper;
import org.j4el.com.model.TaskDto;
import org.j4el.com.repository.TaskRepository;
import org.j4el.com.service.TaskService;
import org.j4el.com.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    private TaskDto task;

    @InjectMocks
    private TaskService taskService = new TaskServiceImpl(taskMapper, taskRepository);


    @BeforeEach
    public void setUp() {
        taskService = new TaskServiceImpl(taskMapper, taskRepository);
    }


    @Test
    public void testCreateTask() {
        TaskDto taskDto = getTaskDto(LocalDate.now());

        Mockito.when(taskRepository.existsTaskByTitle("Title")).thenReturn(false);
        Mockito.when(taskMapper.maptoEntity(taskDto)).thenReturn(buildEntity());
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(getSavedTaskEntity());

        var task = taskService.createTask(taskDto);
        assertThat(task.getId()).isNotNull();
    }

    @Test
    public void testCreateTaskTitleExist() {
        TaskDto taskDto = getTaskDto(LocalDate.now());
        Mockito.when(taskRepository.existsTaskByTitle("Title")).thenReturn(true);
        assertThatExceptionOfType(TaskException.class).isThrownBy(() -> taskService.createTask(taskDto));
    }

    @Test
    public void testCreateTaskScheduleDateBefore() {
        TaskDto taskDto = getTaskDto(LocalDate.now().minus(1, ChronoUnit.DAYS));
        assertThatExceptionOfType(TaskException.class).isThrownBy(() -> taskService.createTask(taskDto));
    }

    @Test
    public void testCreateTaskScheduleDateAfter() {
        TaskDto taskDto = getTaskDto(LocalDate.now().plus(1, ChronoUnit.DAYS));
        Mockito.when(taskRepository.existsTaskByTitle("Title")).thenReturn(false);
        Mockito.when(taskMapper.maptoEntity(taskDto)).thenReturn(buildEntity());
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(getSavedTaskEntity());
        var task = taskService.createTask(taskDto);
        assertThat(task.getId()).isNotNull();
    }

    @Test
    public void getTask() {

    }

    @Test
    public void testUpdateTaskSuccess() {
        task = getTaskDto(LocalDate.now().plusDays(10));
        task.setStatus(Task.Status.NOT_COMPLETED.toString());
        Mockito.when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getSavedTaskEntity()));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(getSavedTaskEntity());
        var updatedTask = taskService.updateTask("123", task);
        assertThat(updatedTask.equals(getSavedTaskEntity()));
    }

    @Test
    public void testUpdateTaskStatusCompleted() {
        assertThatExceptionOfType(TaskException.class).isThrownBy(() -> taskService.updateTask("123", task));
    }

    @Test
    public void testDeleteTaskSucccess() {
        Mockito.when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getSavedTaskEntity()));
        taskService.deleteTask("123");
        Mockito.verify(taskRepository, Mockito.times(1)).delete(Mockito.any());
    }
    @Test
    public void testDeleteTaskStatusCompleted() {
        Mockito.when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(buildEntity()));
        assertThatExceptionOfType(TaskException.class).isThrownBy(() -> taskService.updateTask("123", task));
    }

    private Task getSavedTaskEntity() {
        return Task.builder()
                .id(1L)
                .title("Title")
                .description("This is a description")
                .status(Task.Status.NOT_COMPLETED)
                .scheduledDate(LocalDateTime.now())
                .build();
    }


    private Task buildEntity() {
        return Task.builder()
                .title("Title not exist")
                .description("This is a description")
                .status(Task.Status.COMPLETED)
                .build();
    }

    private TaskDto getTaskDto(LocalDate now) {
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Title");
        taskDto.setDescription("This is a description");
        taskDto.setStatus(Task.Status.COMPLETED.name());
        taskDto.setScheduledDate(now);
        return taskDto;
    }

}
