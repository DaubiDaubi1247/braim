package ru.alex.braim.utils;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {
    public static OffsetDateTime offsetDateTimeWithoutOffset(OffsetDateTime offsetDateTime) {

        if (offsetDateTime == null) {
            return null;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return OffsetDateTime.parse(offsetDateTime.format(dateTimeFormatter));
    }
}
