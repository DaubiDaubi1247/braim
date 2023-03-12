package ru.alex.braim.requestParam;

import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class FromSizeParams {
    @Min(value = 0, message = "from cant be less than zero")
    protected Integer from = 0;

    @Min(value = 1, message = "from cant be less than one")
    protected Integer size = 10;
}
