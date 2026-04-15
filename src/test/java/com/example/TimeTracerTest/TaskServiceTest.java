package com.example.TimeTracerTest;

import com.example.TimeTracerTest.entity.DTO.CreateTaskRequestDto;
import com.example.TimeTracerTest.entity.Status;
import com.example.TimeTracerTest.entity.Task;
import com.example.TimeTracerTest.repository.TaskRepository;
import com.example.TimeTracerTest.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskService taskService;

    private CreateTaskRequestDto requestDto;
    private Task task;

    @BeforeEach
    void setUp() {
        requestDto = new CreateTaskRequestDto();
        requestDto.setTitle("Тестовая задача");
        requestDto.setDescription("Тестовое описание");
        requestDto.setStatus(Status.NEW);

        task = new Task();
        task.setId(1L);
        task.setTitle("Тестовая задача");
        task.setDescription("Тестовое описание");
        task.setStatus(Status.NEW);
    }

    @Test
    void createTask_ShouldSaveTask() {
        when(modelMapper.map(requestDto, Task.class)).thenReturn(task);

        taskService.createTask(requestDto);

        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void findById_ShouldReturnTask_WhenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task found = taskService.findById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("Тестовая задача", found.getTitle());
        assertEquals(Status.NEW, found.getStatus());
    }

    @Test
    void findById_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.findById(999L));
    }

    @Test
    void changeTaskStatus_ShouldUpdateStatus_WhenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.changeTaskStatus(Status.DONE, 1L);

        assertEquals(Status.DONE, task.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void changeTaskStatus_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.changeTaskStatus(Status.DONE, 999L));
    }

    @Test
    void changeTaskStatus_ShouldUpdateToInProgress() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.changeTaskStatus(Status.IN_PROGRESS, 1L);

        assertEquals(Status.IN_PROGRESS, task.getStatus());
        verify(taskRepository, times(1)).save(task);
    }
}