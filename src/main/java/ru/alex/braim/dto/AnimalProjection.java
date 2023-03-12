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

    @Value("#{target.chippingInfo.getChipper().getId()}")
    Integer getChipperId();

    @Value("#{target.chippingInfo.getChippingDateTime()}")
    Date getChippingDateTime();

    @Value("#{target.chippingInfo.getLocationInfo().getId()}")
    Long getChippingLocationId();
//
   @Value("#{@listUtils.toLongList(target.getAnimalTypeList())}")
   List<Long> getAnimalTypes();
//
    @Value("#{@listUtils.toLongList(target.getAnimalLocations())}")
    List<Long> getVisitedLocations();
//
    @Value("#{target.chippingInfo.getDeathDateTime()}")
    Date getDeathDateTime();
}
