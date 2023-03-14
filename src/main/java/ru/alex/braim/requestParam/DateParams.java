package ru.alex.braim.requestParam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class DateParams extends RangeParams {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected OffsetDateTime startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected OffsetDateTime endDateTime;
}
