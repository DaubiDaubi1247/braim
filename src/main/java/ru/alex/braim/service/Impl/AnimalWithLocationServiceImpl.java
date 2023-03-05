package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.LocationPointDto;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.entity.LocationInfo;
import ru.alex.braim.exception.IncompatibleData;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.requestParam.DateRequestParams;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.service.AnimalWithLocationService;
import ru.alex.braim.service.LocationService;
import ru.alex.braim.utils.enums.LifeStatusEnum;

import java.util.List;

import static ru.alex.braim.service.Impl.LocationServiceImpl.isIncompatibleData;

@Service
@RequiredArgsConstructor
@Validated
public class AnimalWithLocationServiceImpl implements AnimalWithLocationService {

    private final AnimalService animalService;
    private final LocationService locationService;

    @Override
    @Transactional
    public LocationProjection addLocationToAnimal(@Id Long animalId, @Id Long pointId) {

        Animal animal = animalService.getAnimalEntityById(pointId);
        LocationInfo locationInfo = locationService.getLocationEntityById(pointId);

        if (animal.getLifeStatus().equals(LifeStatusEnum.DEAD.getLifeStatus())) {
            throw new IncompatibleData();
        }

        if (animal.getLocationList().size() == 1) {
            throw new IncompatibleData();
        }

        if (animal.getLocationList().get(animal.getLocationList().size() - 1).equals(locationInfo)) {
            throw new IncompatibleData();
        }

        animal.getLocationList().add(locationInfo);
        animalService.saveAnimal(animal);

        return locationService.findLocationProjectionByAnimalId(animalId);
    }

    @Override
    @Transactional
    public LocationProjection updateLocationPoint(@Id Long animalId, @Valid LocationPointDto locationInfoDto) {
        Animal animal = animalService.getAnimalEntityById(animalId);
        LocationInfo locationInfo = locationService.getLocationEntityById(locationInfoDto.getVisitedLocationPointId());
        LocationInfo newLocationInfo = locationService.getLocationEntityById(locationInfoDto.getLocationPointId());

        int indexUpdatedPoint = animal.getLocationList().indexOf(locationInfo);

        if (isIncompatibleData(locationInfoDto, animal, indexUpdatedPoint)) {
            throw new IncompatibleData();
        }

        if (!animal.getLocationList().contains(locationInfo)) {
            throw new NotFoundException("");
        }

        animal.getLocationList().set(indexUpdatedPoint, newLocationInfo);
        animalService.saveAnimal(animal);

        return locationService.findLocationProjectionByAnimalId(animalId);
    }

    @Override
    @Transactional
    public void deleteLocationPointFromAnimal(@Id Long animalId, @Id Long visitedPointId) {
        Animal animal = animalService.getAnimalEntityById(animalId);
        LocationInfo locationInfo = locationService.getLocationEntityById(visitedPointId);

        int indexDelLocation = animal.getLocationList().indexOf(locationInfo);
        if (indexDelLocation == -1) {
            throw new NotFoundException("");
        }

        if (indexDelLocation == 0) {
            if (animal.getLocationList().get(indexDelLocation).equals(animal.getChippingInfo().getLocationInfo())) {
                animal.getLocationList().subList(0, 2).clear();
            }
        }

        animal.getLocationList().remove(indexDelLocation);

    }

    @Override
    @Transactional
    public List<LocationProjection> getLocationVisitedPointList(@Valid DateRequestParams dateRequestParams,
                                                                @Id Long id) {
        if (animalService.animalExistById(id)) {
            throw new NotFoundException("animal with id = " + id + " not found");
        }

        return locationService.getLocationVisitedPointList(dateRequestParams, id);
    }
}
