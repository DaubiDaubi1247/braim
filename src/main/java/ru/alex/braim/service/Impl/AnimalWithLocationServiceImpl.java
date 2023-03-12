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
import ru.alex.braim.entity.AnimalLocation;
import ru.alex.braim.entity.LocationInfo;
import ru.alex.braim.exception.IncompatibleData;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.requestParam.DateRequestParams;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.service.AnimalWithLocationService;
import ru.alex.braim.service.LocationService;
import ru.alex.braim.utils.ListUtils;
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

        Animal animal = animalService.getAnimalEntityById(animalId);
        LocationInfo locationInfo = locationService.getLocationEntityById(pointId);

        if (animal.getLifeStatus().equals(LifeStatusEnum.DEAD.getLifeStatus())) {
            throw new IncompatibleData("animal died");
        }

        if (animal.getAnimalLocations().size() == 0) {
            if (animal.getChippingInfo().getLocationInfo().equals(locationInfo)) {
                throw new IncompatibleData("animal now in chipping location");
            }
        }

        if (isAnimalNotMove(animal, locationInfo)) {
            throw new IncompatibleData("animal not move");
        }

        if (isSamePoints(animal, locationInfo)) {
            throw new IncompatibleData("try update point to the same");
        }

        AnimalLocation animalLocation = new AnimalLocation(locationInfo);
        animalLocation.setAnimal(animal);

        animal.addLocationToAnimal(animalLocation);
        animalService.flushAnimal();

        return locationService.findLocationProjectionByAnimalId(animalLocation.getId());
    }

    private static boolean isSamePoints(Animal animal, LocationInfo locationInfo) {
        return animal.getAnimalLocations().size() != 0 &&
                animal.getAnimalLocations().
                        get(animal.getAnimalLocations().size() - 1).
                        getLocationInfo().
                        equals(locationInfo);
    }

    private static boolean isAnimalNotMove(Animal animal, LocationInfo locationInfo) {

        if (animal.getAnimalLocations().size() == 0) {
            return false;
        }

        LocationInfo lastVisitedLocation = animal.getAnimalLocations()
                .get(animal.getAnimalLocations().size() - 1)
                .getLocationInfo();

        System.out.println("\n\n\n\n last visited id == " + lastVisitedLocation.getId() + " \n\n\n\n");

        return lastVisitedLocation.equals(locationInfo);
    }

    @Override
    @Transactional
    public LocationProjection updateLocationPoint(@Id Long animalId, @Valid LocationPointDto locationInfoDto) {
        Animal animal = animalService.getAnimalEntityById(animalId);
        LocationInfo newLocationInfo = locationService.getLocationEntityById(locationInfoDto.getLocationPointId());

        int animalLocationIndex = ListUtils.indexOfById(animal.getAnimalLocations(),
                locationInfoDto.getVisitedLocationPointId());

        if (animalLocationIndex == -1) {
            throw new NotFoundException("animalLocation with id =" + locationInfoDto.getVisitedLocationPointId() + " not found");
        }

        if (isIncompatibleData(locationInfoDto, animal, animalLocationIndex)) {
            throw new IncompatibleData("point already nearby");
        }

        animal.getAnimalLocations().get(animalLocationIndex).setLocationInfo(newLocationInfo);
        animalService.flushAnimal();

        return locationService.findLocationProjectionByAnimalId(animal.getAnimalLocations()
                .get(animalLocationIndex)
                .getId());
    }

    @Override
    @Transactional
    public void deleteLocationPointFromAnimal(@Id Long animalId, @Id Long visitedPointId) {
        Animal animal = animalService.getAnimalEntityById(animalId);

        int indexDelLocation = ListUtils.indexOfById(animal.getAnimalLocations(), visitedPointId);
        if (indexDelLocation == -1) {
            throw new NotFoundException("location Info with id = " + visitedPointId + " not found" );
        }

        if (indexDelLocation == 0 && animal.getAnimalLocations().size() > 1) {
            if (animal.getAnimalLocations()
                    .get(indexDelLocation + 1)
                    .getLocationInfo()
                    .equals(animal.getChippingInfo().getLocationInfo())) {

                animal.getAnimalLocations().subList(0, 2).clear();
            }
        } else {
            animal.getAnimalLocations().remove(indexDelLocation);

        }

        animalService.flushAnimal();

    }

    @Override
    @Transactional
    public List<LocationProjection> getLocationVisitedPointList(@Valid DateRequestParams dateRequestParams,
                                                                @Id Long id) {
        if (!animalService.animalExistById(id)) {
            throw new NotFoundException("animal with id = " + id + " not found");
        }

        return locationService.getLocationVisitedPointList(dateRequestParams, id);
    }
}
