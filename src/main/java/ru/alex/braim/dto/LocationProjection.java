package ru.alex.braim.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface LocationProjection {

    Long getId();

    @Value("#{target.visitedDate}")
    Date getDateTimeOfVisitLocationPoint();

    @Value("#{target.locationInfo.getId()}")
    Long getLocationPointId();
}
