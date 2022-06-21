package org.j4el.com.controller;

import lombok.RequiredArgsConstructor;
import org.j4el.com.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task/")
public class TaskController {
    private final TaskService taskService;

}
