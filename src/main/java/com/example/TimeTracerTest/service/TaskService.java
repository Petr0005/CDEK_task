package com.example.TimeTracerTest.service;

import com.example.TimeTracerTest.entity.DTO.CreateTaskRequestDto;
import com.example.TimeTracerTest.entity.Status;
import com.example.TimeTracerTest.entity.Task;
import com.example.TimeTracerTest.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public void createTask(CreateTaskRequestDto requestDto) {
        Task task = modelMapper.map(requestDto, Task.class);

        taskRepository.save(task);
    }

    public Task findById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    public void changeTaskStatus(Status status, long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setStatus(status);

        taskRepository.save(task);
    }
}
