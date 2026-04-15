package com.example.TimeTracerTest.controller;

import com.example.TimeTracerTest.entity.DTO.CreateTimeRecordRequestDto;
import com.example.TimeTracerTest.entity.TimeRecord;
import com.example.TimeTracerTest.service.TimeRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/timeRecords")
@RequiredArgsConstructor
public class TimeRecordController {
    private final TimeRecordService timeRecordService;


    @PostMapping
    public ResponseEntity<Void> createTimeRecord(@RequestBody @Valid CreateTimeRecordRequestDto requestDto) {
        timeRecordService.createTimeRecord(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<TimeRecord>> findTimeRecords(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(timeRecordService.findByStartAndEndTimes(start, end));
    }
}
