package ru.alex.braim.requestParam;

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
public class DateRequestParams extends FromSizeParams {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected Date startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected Date endDateTime;
}
