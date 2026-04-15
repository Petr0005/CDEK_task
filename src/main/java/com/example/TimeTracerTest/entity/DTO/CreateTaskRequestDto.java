package com.example.TimeTracerTest.entity.DTO;

import com.example.TimeTracerTest.entity.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTaskRequestDto {

    @NotNull
    private String title;

    @NotEmpty(message = "Описание не должно быть пустым")
    private String description;

    @NotNull(message = "Должен быть статус")
    private Status status;
}
