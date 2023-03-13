package ru.alex.braim.utils;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {
    public static OffsetDateTime offsetDateTime(OffsetDateTime offsetDateTime) {

        if (offsetDateTime == null) {
            return null;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        System.out.println(OffsetDateTime.parse(offsetDateTime.format(dateTimeFormatter)));

        return OffsetDateTime.parse(offsetDateTime.format(dateTimeFormatter));
    }
}
