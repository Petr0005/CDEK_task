package com.example.TimeTracerTest.entity.DTO;

import com.example.TimeTracerTest.entity.Task;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateTimeRecordRequestDto {

    @NotNull
    private long employeeId;

    @NotNull
    private Task task;

    @FutureOrPresent
    private LocalDateTime startTime;

    @FutureOrPresent
    private LocalDateTime endTime;

    @NotEmpty
    private String work_description;
}
