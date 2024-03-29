package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.LocationInfoDto;
import ru.alex.braim.dto.LocationPointDto;
import ru.alex.braim.dto.Projection.LocationProjection;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.entity.AnimalLocation;
import ru.alex.braim.entity.LocationInfo;
import ru.alex.braim.exception.AlreadyExistException;
import ru.alex.braim.exception.ConnectionWithAnimal;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.LocationInfoMapper;
import ru.alex.braim.repository.LocationInfoRepository;
import ru.alex.braim.requestParam.DateParams;
import ru.alex.braim.service.LocationService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Validated
public class LocationServiceImpl implements LocationService {

    private final LocationInfoRepository locationInfoRepository;
    private final LocationInfoMapper locationInfoMapper;

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
        locationInfo.setLongitude(locationInfoDto.getLongitude());

        return locationInfoMapper.toDto(locationInfoRepository.save(locationInfo));
    }

    public static boolean isIncompatibleData(LocationPointDto locationInfoDto, Animal animal, int indexUpdatedPoint) {

        return  (indexUpdatedPoint == 0 && isChippingLocation(locationInfoDto, animal)) ||
                isPointIsAlreadyNearby(locationInfoDto, animal, indexUpdatedPoint) ||
                isEqualsPoints(locationInfoDto, animal.getAnimalLocations().get(indexUpdatedPoint));
    }

    private static boolean isEqualsPoints(LocationPointDto locationInfoDto, AnimalLocation animalLocation) {
        return Objects.equals(animalLocation.getLocationInfo().getId(), locationInfoDto.getLocationPointId());
    }

    private static boolean isPointIsAlreadyNearby(LocationPointDto locationInfoDto, Animal animal, int indexUpdatedPoint) {
        return isEqualsPreviousPoint(locationInfoDto, animal, indexUpdatedPoint) ||
                isEqualsNextPoint(locationInfoDto, animal, indexUpdatedPoint);
    }

    private static boolean isEqualsNextPoint(LocationPointDto locationInfoDto, Animal animal, int indexUpdatedPoint) {
        return indexUpdatedPoint < animal.getAnimalLocations().size() - 1 &&
                Objects.equals(animal.getAnimalLocations().get(indexUpdatedPoint + 1).getLocationInfo().getId(),
                    locationInfoDto.getLocationPointId());
    }

    private static boolean isEqualsPreviousPoint(LocationPointDto locationInfoDto, Animal animal, int indexUpdatedPoint) {
        return indexUpdatedPoint > 0 && Objects.equals(animal.getAnimalLocations().get(indexUpdatedPoint - 1).getLocationInfo().getId(),
                locationInfoDto.getLocationPointId());
    }

    private static boolean isChippingLocation(LocationPointDto locationInfoDto, Animal animal) {
        return Objects.equals(animal.getChippingInfo().getLocationInfo().getId(),
                locationInfoDto.getLocationPointId());
    }

    @Override
    @Transactional
    public void deleteLocation(@Id Long id) {
        LocationInfo locationInfo = getLocationEntityById(id);

        if (isLocationConnectWithAnima(locationInfo)) {
            throw new ConnectionWithAnimal("location with id = " + id + " connection with animal");
        }
//        locationInfo.getChippingInfo().clear();

        locationInfoRepository.delete(locationInfo);
    }

    private static boolean isLocationConnectWithAnima(LocationInfo locationInfo) {
        return locationInfo.getAnimalLocationList().size() != 0 || locationInfo.getChippingInfo().size() != 0;
    }

    @Override
    @Transactional
    public LocationProjection findLocationProjectionByAnimalId(@Id Long animalLocationId) {
        return locationInfoRepository.findLocationProjectionByAnimalId(animalLocationId);
    }

    private boolean existByLatitudeAndLongitude(LocationInfoDto locationInfoDto) {
        return locationInfoRepository.existsByLatitudeAndLongitude(locationInfoDto.getLatitude(),
                locationInfoDto.getLongitude());
    }

    @Override
    @Transactional
    public List<LocationProjection> getLocationVisitedPointList(@Valid DateParams dateRequestParams,
                                                                @Id Long id) {

        Pageable pageable = PageRequest.of(dateRequestParams.getFrom(), dateRequestParams.getSize());

//        DateParamsForSql dateParamsForSql = new DateParamsForSql(dateRequestParams.getStartDateTime(), dateRequestParams.getEndDateTime());

        Page<LocationProjection> locationProjectionList = locationInfoRepository
                .findLocationPointByParams(dateRequestParams.getStartDateTime(), dateRequestParams.getEndDateTime(), id, pageable);

        return locationProjectionList.getContent();
    }

    @Override
    @Transactional
    public LocationInfo getLocationEntityById(Long id) {
        return locationInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("location with id = " + id + " not found"));
    }
}
