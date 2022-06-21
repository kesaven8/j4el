package org.j4el.com.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDto {
    private Long id;

    private String title;

    private String description;

    private LocalDateTime scheduledDate;

    private String location;

    private String status;
}
