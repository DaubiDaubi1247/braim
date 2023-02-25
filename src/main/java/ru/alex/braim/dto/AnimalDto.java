package ru.alex.braim.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AnimalDto {

    @NotNull
    private Long id;

    @NotNull(message = "weight cant be empty")
    @DecimalMin(value = "0.0", inclusive = false,
        message = "weight cant less than 0")
    private Float weight;
    @NotNull(message = "length cant be empty")
    @DecimalMin(value = "0.0", inclusive = false,
        message = "length cant less than 0")
    private Float length;

    @NotNull(message = "height cant be empty")
    @DecimalMin(value = "0.0", inclusive = false,
        message = "height cant less than 0")
    private Float height;

    @NotBlank(message = "gender cant be empty")
    @Enumerated(EnumType.STRING)
    private String gender;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private String lifeStatus;

    @NotNull(message = "animalsTypes cant be null")
    @Size.List({@Size (min = 1)})
    private List<Long> animalsTypes;
}
