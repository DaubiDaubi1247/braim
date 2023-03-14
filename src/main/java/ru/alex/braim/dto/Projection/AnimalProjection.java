package ru.alex.braim.dto.Projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.OffsetDateTime;
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

    @Value("#{@dateUtils.offsetDateTimeWithoutOffset(target.chippingInfo.getChippingDateTime())}")
    OffsetDateTime getChippingDateTime();

    @Value("#{target.chippingInfo.getLocationInfo().getId()}")
    Long getChippingLocationId();
//
   @Value("#{@listUtils.toLongList(target.getAnimalTypeList())}")
   List<Long> getAnimalTypes();
//
    @Value("#{@listUtils.toLongList(target.getAnimalLocations())}")
    List<Long> getVisitedLocations();
//
    @Value("#{@dateUtils.offsetDateTimeWithoutOffset(target.chippingInfo.getDeathDateTime())}")
    OffsetDateTime getDeathDateTime();
}
