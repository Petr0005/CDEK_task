package com.example.TimeTracerTest;

import com.example.TimeTracerTest.entity.DTO.CreateTimeRecordRequestDto;
import com.example.TimeTracerTest.entity.Task;
import com.example.TimeTracerTest.entity.TimeRecord;
import com.example.TimeTracerTest.repository.TimeRecordRepository;
import com.example.TimeTracerTest.service.TimeRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeRecordServiceTest {

    @Mock
    private TimeRecordRepository timeRecordRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TimeRecordService timeRecordService;

    private CreateTimeRecordRequestDto requestDto;
    private TimeRecord timeRecord;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Тестовая задача");

        requestDto = new CreateTimeRecordRequestDto();
        requestDto.setEmployeeId(100L);
        requestDto.setTask(task);
        requestDto.setStartTime(LocalDateTime.now().plusHours(1));
        requestDto.setEndTime(LocalDateTime.now().plusHours(2));
        requestDto.setWork_description("Тестовое описание работы");

        timeRecord = new TimeRecord();
        timeRecord.setId(1L);
        timeRecord.setEmployeeId(100L);
        timeRecord.setTask(task);
        timeRecord.setStartTime(LocalDateTime.now().plusHours(1));
        timeRecord.setEndTime(LocalDateTime.now().plusHours(2));
        timeRecord.setWork_description("Тестовое описание работы");

        startTime = LocalDateTime.of(2024, 1, 1, 10, 0);
        endTime = LocalDateTime.of(2024, 1, 1, 18, 0);
    }

    @Test
    void createTimeRecord_ShouldSaveTimeRecord() {
        when(modelMapper.map(requestDto, TimeRecord.class)).thenReturn(timeRecord);

        timeRecordService.createTimeRecord(requestDto);

        verify(timeRecordRepository, times(1)).save(timeRecord);
    }

    @Test
    void findByStartAndEndTimes_ShouldReturnTimeRecords_WhenRecordsExist() {
        List<TimeRecord> expectedRecords = Arrays.asList(timeRecord);
        when(timeRecordRepository.findByStartTimeAfterAndEndTimeBefore(startTime, endTime))
                .thenReturn(expectedRecords);

        List<TimeRecord> result = timeRecordService.findByStartAndEndTimes(startTime, endTime);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedRecords, result);
        verify(timeRecordRepository, times(1)).findByStartTimeAfterAndEndTimeBefore(startTime, endTime);
    }

    @Test
    void findByStartAndEndTimes_ShouldThrowException_WhenNoRecordsFound() {
        when(timeRecordRepository.findByStartTimeAfterAndEndTimeBefore(startTime, endTime))
                .thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () ->
                timeRecordService.findByStartAndEndTimes(startTime, endTime));
        verify(timeRecordRepository, times(1)).findByStartTimeAfterAndEndTimeBefore(startTime, endTime);
    }

    @Test
    void findByStartAndEndTimes_ShouldReturnMultipleRecords_WhenManyExist() {
        TimeRecord timeRecord2 = new TimeRecord();
        timeRecord2.setId(2L);
        timeRecord2.setEmployeeId(200L);

        List<TimeRecord> expectedRecords = Arrays.asList(timeRecord, timeRecord2);
        when(timeRecordRepository.findByStartTimeAfterAndEndTimeBefore(startTime, endTime))
                .thenReturn(expectedRecords);

        List<TimeRecord> result = timeRecordService.findByStartAndEndTimes(startTime, endTime);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(timeRecordRepository, times(1)).findByStartTimeAfterAndEndTimeBefore(startTime, endTime);
    }

    @Test
    void createTimeRecord_ShouldMapCorrectly() {
        when(modelMapper.map(requestDto, TimeRecord.class)).thenReturn(timeRecord);

        timeRecordService.createTimeRecord(requestDto);

        verify(modelMapper, times(1)).map(requestDto, TimeRecord.class);
        verify(timeRecordRepository, times(1)).save(timeRecord);
    }
}