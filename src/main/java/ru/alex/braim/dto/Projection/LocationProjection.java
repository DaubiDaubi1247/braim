package ru.alex.braim.dto.Projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.OffsetDateTime;

public interface LocationProjection {

    Long getId();

    @Value("#{@dateUtils.offsetDateTimeWithoutOffset(target.visitedDate)}")
    OffsetDateTime getDateTimeOfVisitLocationPoint();

    @Value("#{target.locationInfo.getId()}")
    Long getLocationPointId();
}
