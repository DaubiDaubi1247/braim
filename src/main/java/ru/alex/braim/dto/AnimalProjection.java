package ru.alex.braim.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface AnimalProjection {

    Long getId();
    Float getWeight();
    Float getLength();
    Float getHeight();

    @Value("#{target.getGender().name}")
    String getGender();
    String getLifeStatus();

    @Value("#{target.getChipId}")
    Integer getChipperId();
    Date getChippingDateTime();

    @Value("#{target.getLocationId}")
    Long getChippingLocationId();

    @Value("#{@listUtils.toLongList(target.getLocationList())}")
    List<Long> getVisitedLocations();
    Date getDeathDateTime();
}
