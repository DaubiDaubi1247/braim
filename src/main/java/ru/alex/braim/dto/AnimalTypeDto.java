package ru.alex.braim.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AnimalTypeDto {
    private Long id;

    @NotBlank(message = "type cant be empty")
    private String type;
}
