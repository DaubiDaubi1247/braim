package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.LocationPointDto;
import ru.alex.braim.dto.Projection.LocationProjection;
import ru.alex.braim.requestParam.DateParams;

import java.util.List;

public interface AnimalWithLocationService {

    LocationProjection addLocationToAnimal(@Id Long animalId, @Id Long pointId);

    LocationProjection updateLocationPoint(@Id Long animalId, @Valid LocationPointDto locationInfoDto);

    void deleteLocationPointFromAnimal(@Id Long animalId, @Id Long visitedPointId);

    List<LocationProjection> getLocationVisitedPointList(@Valid DateParams dateRequestParams, @Id Long id);
}
