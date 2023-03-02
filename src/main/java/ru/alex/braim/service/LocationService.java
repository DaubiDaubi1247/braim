package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.LocationInfoDto;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.requestParam.DateRequestParams;

import java.util.List;

public interface LocationService {
    LocationInfoDto getLocationById(@Id Long id);

    LocationInfoDto createLocation(@Valid LocationInfoDto locationInfoDto);

    LocationInfoDto updateLocation(@Valid LocationInfoDto locationInfoDto, @Id Long id);

    void deleteLocation(@Id Long id);

    List<LocationProjection> getLocationVisitedPointList(@Valid DateRequestParams dateRequestParams, @Id Long id);
}
