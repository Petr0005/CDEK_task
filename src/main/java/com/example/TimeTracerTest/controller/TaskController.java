package com.example.TimeTracerTest.controller;

import com.example.TimeTracerTest.entity.DTO.CreateTaskRequestDto;
import com.example.TimeTracerTest.entity.Status;
import com.example.TimeTracerTest.entity.Task;
import com.example.TimeTracerTest.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody @Valid CreateTaskRequestDto requestDto) {
        taskService.createTask(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findTask(@PathVariable long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> changeTaskStatus(@PathVariable long id, @RequestParam Status status) {
        taskService.changeTaskStatus(status, id);

        return ResponseEntity.ok().build();
    }
}
