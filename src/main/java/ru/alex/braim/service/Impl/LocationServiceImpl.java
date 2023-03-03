package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.LocationInfoDto;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.LocationInfo;
import ru.alex.braim.exception.AlreadyExistException;
import ru.alex.braim.exception.ConnectionWithAnimal;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.LocationInfoMapper;
import ru.alex.braim.repository.LocationInfoRepository;
import ru.alex.braim.requestParam.DateRequestParams;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.service.LocationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class LocationServiceImpl implements LocationService {

    private final LocationInfoRepository locationInfoRepository;
    private final LocationInfoMapper locationInfoMapper;

    @Setter
    private AnimalService animalService;

    @Override
    @Transactional
    public LocationInfoDto getLocationById(@Id Long id) {
        return locationInfoMapper.toDto(getLocationEntityById(id));
    }

    @Override
    @Transactional
    public LocationInfoDto createLocation(@Valid LocationInfoDto locationInfoDto) {
        if (existByLatitudeAndLongitude(locationInfoDto)) {
            throw new AlreadyExistException("location with latitude = " + locationInfoDto.getLatitude() +
                    " and longitude = " + locationInfoDto.getLongitude() + " already exist");
        }

        LocationInfo locationInfo = locationInfoMapper.toEntity(locationInfoDto);

        return locationInfoMapper.toDto(locationInfoRepository.save(locationInfo));
    }

    @Override
    @Transactional
    public LocationInfoDto updateLocation(@Valid LocationInfoDto locationInfoDto, @Id Long id) {

        if (existByLatitudeAndLongitude(locationInfoDto)) {
            throw new AlreadyExistException("location with latitude = " + locationInfoDto.getLatitude() +
                    " and longitude = " + locationInfoDto.getLongitude() + " already exist");
        }

        LocationInfo locationInfo = getLocationEntityById(id);
        locationInfo.setLatitude(locationInfoDto.getLatitude());
        locationInfo.setLongitude(locationInfo.getLongitude());

        return locationInfoMapper.toDto(locationInfoRepository.save(locationInfo));
    }

    @Override
    @Transactional
    public void deleteLocation(Long id) {
        LocationInfo locationInfo = getLocationEntityById(id);

        if (locationInfo.getAnimalList().size() != 0) {
            throw new ConnectionWithAnimal("location with id = " + id + " connection with animal");
        }

        locationInfoRepository.delete(locationInfo);
    }

    private boolean existByLatitudeAndLongitude(LocationInfoDto locationInfoDto) {
        return locationInfoRepository.existsByLatitudeAndLongitude(locationInfoDto.getLatitude(),
                locationInfoDto.getLongitude());
    }

    @Override
    @Transactional
    public List<LocationProjection> getLocationVisitedPointList(@Valid DateRequestParams dateRequestParams, @Id Long id) {
        if (animalService.animalExistById(id)) {
            throw new NotFoundException("animal with id = " + id + " not found");
        }

        Pageable pageable = PageRequest.of(dateRequestParams.getFrom(), dateRequestParams.getSize());

        Page<LocationProjection> locationProjectionList =  locationInfoRepository
                .findLocationPointByParams(dateRequestParams.getStartDateTime(),
                        dateRequestParams.getEndDateTime(), id, pageable);

        return locationProjectionList.getContent();
    }

    @Override
    @Transactional
    public LocationInfo getLocationEntityById(Long id) {
        return locationInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("location with id = " + id + " not found"));
    }
}
