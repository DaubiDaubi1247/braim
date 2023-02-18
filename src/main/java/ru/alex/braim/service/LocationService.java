package ru.alex.braim.service;

import ru.alex.braim.dto.LocationInfoDto;

public interface LocationService {
    LocationInfoDto getLocationById(Long id);
}
