package org.j4el.com.mapper;

import org.j4el.com.entity.Task;
import org.j4el.com.model.CreateTaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    CreateTaskDto maptoDto(Task task);

    @Mapping(target = "scheduledDate",qualifiedByName = "mapToLocalDateTime")
    Task maptoEntity(CreateTaskDto taskDto);

    @Named("mapToLocalDateTime")
    default LocalDateTime mapToLocalDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.now());
    }
}
