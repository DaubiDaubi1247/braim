package ru.alex.braim.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.annotation.IdList;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AnimalDto {

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

    private String lifeStatus;

//    @NotNull(message = "animalsTypes cant be null")
//    @Size.List({@Size (min = 1)})
//    @IdList
    private List<Long> animalTypes;

    @NotNull
    @Min(1)
    private Integer chipperId;

    @Id
    private Long chippingLocationId;
}
