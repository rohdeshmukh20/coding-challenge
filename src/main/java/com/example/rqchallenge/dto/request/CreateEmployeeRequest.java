package com.example.rqchallenge.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {
    @NotNull(message = "name cannot be empty")
    private String name;

    @NotNull(message = "salary cannot be empty")

    private String salary;

    @NotNull(message = "age cannot be empty")
    private String age;
}
