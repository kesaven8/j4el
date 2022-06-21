package org.j4el.com.mapper;

import org.j4el.com.dto.TaskDto;
import org.j4el.com.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto maptoDto(Task task);

    Task maptoEntity(TaskDto taskDto);
}
