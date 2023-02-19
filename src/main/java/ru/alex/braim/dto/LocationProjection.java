package ru.alex.braim.dto;

import java.util.Date;

public interface LocationProjection {
    Long getId();

    Date getDateTimeOfVisitLocationPoint();

    Long getLocationPointId();
}
