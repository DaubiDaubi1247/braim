package ru.alex.braim.requestParam;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class DateRequestParams {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected Date startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected Date endDateTime;

    @Min(0)
    protected Integer from;

    @Min(1)
    protected Integer size;
}
