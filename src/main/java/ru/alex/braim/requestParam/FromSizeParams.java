package ru.alex.braim.requestParam;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class FromSizeParams {
    @Min(value = 0, message = "from cant be less than zero")
    protected Integer from;

    @Min(value = 1, message = "from cant be less than one")
    protected Integer size;
}
