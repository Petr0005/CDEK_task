package com.example.TimeTracerTest.service;

import com.example.TimeTracerTest.entity.DTO.CreateTimeRecordRequestDto;
import com.example.TimeTracerTest.entity.TimeRecord;
import com.example.TimeTracerTest.repository.TimeRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeRecordService {
    private final TimeRecordRepository timeRecordRepository;
    private final ModelMapper modelMapper;

    public void createTimeRecord(CreateTimeRecordRequestDto requestDto) {
        modelMapper.typeMap(CreateTimeRecordRequestDto.class, TimeRecord.class)
                .addMappings(mapper -> mapper.skip(TimeRecord::setId));

        TimeRecord timeRecord = modelMapper.map(requestDto, TimeRecord.class);
        timeRecordRepository.save(timeRecord);
    }

    public List<TimeRecord> findByStartAndEndTimes(LocalDateTime start, LocalDateTime end) {
        List<TimeRecord> timeRecords = timeRecordRepository.findByStartTimeAfterAndEndTimeBefore(start, end);

        if (timeRecords.isEmpty()) {
            throw new EntityNotFoundException("Not TimeRecords found");
        }

        return timeRecords;
    }
}
