package ru.alex.braim.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.alex.braim.annotation.EnumValue;
import ru.alex.braim.utils.enums.GenderEnum;

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
    @EnumValue(enumClass = GenderEnum.class, message = "gender not contains in genders")
    private String gender;

    @NotBlank
    private String lifeStatus;

}
