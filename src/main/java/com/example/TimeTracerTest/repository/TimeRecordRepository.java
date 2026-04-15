package com.example.TimeTracerTest.repository;

import com.example.TimeTracerTest.entity.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {
    List<TimeRecord> findByStartTimeAfterAndEndTimeBefore(LocalDateTime start, LocalDateTime end);
}
