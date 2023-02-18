package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.braim.dto.LocationInfoDto;
import ru.alex.braim.entity.LocationInfo;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.LocationInfoMapper;
import ru.alex.braim.repository.LocationInfoRepository;
import ru.alex.braim.service.LocationService;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationInfoRepository locationInfoRepository;
    private final LocationInfoMapper locationInfoMapper;

    @Override
    @Transactional
    public LocationInfoDto getLocationById(Long id) {
        return locationInfoMapper.toDto(getLocationInfoEntityById(id));
    }

    private LocationInfo getLocationInfoEntityById(Long id) {
        return locationInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("location with id = " + id + " not found"));
    }
}
