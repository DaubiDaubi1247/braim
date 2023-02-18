package ru.alex.braim.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface AnimalProjection {

    Long getId();
    Float getWeight();
    Float getLength();
    Float getHeight();
    String getGender();
    String getLifeStatus();

    @Value("#{target.chip_info.chipper_id}")
    Integer getChipperId();

    Date getChippingDateTime();
    Long getChippingLocationId();

    @Value("#{@listUtils.toLongList(target.animal_chipping)}")
    List<Long> getVisitedLocations();
    Date getDeathDateTime();
}
