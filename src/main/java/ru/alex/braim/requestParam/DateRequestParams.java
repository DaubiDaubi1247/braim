package ru.alex.braim.requestParam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class DateRequestParams extends FromSizeParams {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected Date startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected Date endDateTime;
}
