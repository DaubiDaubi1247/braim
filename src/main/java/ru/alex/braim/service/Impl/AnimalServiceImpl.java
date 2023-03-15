package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AnimalDto;
import ru.alex.braim.dto.Projection.AnimalProjection;
import ru.alex.braim.dto.TypesDto;
import ru.alex.braim.entity.*;
import ru.alex.braim.exception.AlreadyExistException;
import ru.alex.braim.exception.ConnectionWithAnimal;
import ru.alex.braim.exception.IncompatibleData;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AnimalMapper;
import ru.alex.braim.repository.AnimalRepository;
import ru.alex.braim.requestParam.AnimalParams;
import ru.alex.braim.service.AccountService;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.service.AnimalTypeService;
import ru.alex.braim.service.LocationService;
import ru.alex.braim.utils.enums.LifeStatusEnum;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Validated
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalTypeService animalTypeService;
    private final AccountService accountService;

    private final LocationService locationService;

    private final AnimalMapper animalMapper;

    @Override
    @Transactional
    public AnimalProjection getAnimalById(@Id Long id) {

        if (!animalExistById(id)) {
            throw new NotFoundException("animal with id = " + id + " no found");
        }

        return animalRepository.getAnimalProjectionById(id);
    }

    @Override
    @Transactional
    public AnimalProjection createAnimal(@Valid AnimalDto animalDto) {
        List<AnimalType> animalTypeList = animalTypeService.getAnimalTypeList(animalDto.getAnimalTypes());

        if (animalTypeList.size() != animalDto.getAnimalTypes().size()) {
            throw new NotFoundException("1 or more type not found");
        }

        Account account = accountService.getAccountEntityById(Long.valueOf(animalDto.getChipperId()));
        LocationInfo locationInfo = locationService.getLocationEntityById(animalDto.getChippingLocationId());

        Animal newAnimal = animalMapper.toEntity(animalDto);
        ChippingInfo chippingInfo = new ChippingInfo();
        chippingInfo.setChipper(account);
        chippingInfo.setLocationInfo(locationInfo);

        for (AnimalType animalType : animalTypeList) {
            animalType.getAnimalList().add(newAnimal);
        }

        newAnimal.setAnimalTypeList(animalTypeList);

        newAnimal.setLifeStatus(LifeStatusEnum.ALIVE.getLifeStatus());
        newAnimal.setChippingInfo(chippingInfo);

        chippingInfo.setAnimal(newAnimal);

        animalRepository.save(newAnimal);

        return animalRepository.getAnimalProjectionById(newAnimal.getId());
    }

    @Override
    @Transactional
    public AnimalProjection updateAnimal(@Valid AnimalDto animalDto, @Id Long id) {
        Account account = accountService.getAccountEntityById(Long.valueOf(animalDto.getChipperId()));
        LocationInfo locationInfo = locationService.getLocationEntityById(animalDto.getChippingLocationId());
        Animal animal = getAnimalEntityById(id);

        if (setAliveForDeadAnimal(animalDto, animal)) {
            throw new IncompatibleData();
        }

        if (animal.getAnimalLocations().size() > 0 && Objects.equals(animal.getAnimalLocations().get(0).getLocationInfo().getId(), animalDto.getChippingLocationId())) {
            throw new IncompatibleData("new chipping id == first visited point");
        }

        animal.setGender(animalDto.getGender());
        animal.setHeight(animalDto.getHeight());
        animal.setWeight(animalDto.getWeight());
        animal.setLength(animalDto.getLength());

        ChippingInfo newChippingInfo = new ChippingInfo();
        newChippingInfo.setChipper(account);
        newChippingInfo.setLocationInfo(locationInfo);

        animal.setChippingInfo(newChippingInfo);

        animal.setLifeStatus(animalDto.getLifeStatus());

        if (isDie(animalDto, animal)) {
            animal.getChippingInfo().setDeathDateTime(OffsetDateTime.now());
        }

        return animalRepository.getAnimalProjectionById(id);
    }

    private static boolean isDie(AnimalDto animalDto, Animal animal) {
        return animalDto.getLifeStatus().equals(LifeStatusEnum.DEAD.getLifeStatus())
                && animal.getChippingInfo().getChippingDateTime() == null;
    }

    @Override
    @Transactional
    public AnimalProjection addTypeToAnimal(@Id Long animalId, @Id Long typeId) {
        Animal animal = getAnimalEntityById(animalId);
        AnimalType animalType = animalTypeService.getAnimalTypeEntityById(typeId);

        throwIfTypesAlreadyExist(animal, animalType);

        animal.getAnimalTypeList().add(animalType);
        animalRepository.save(animal);

        return animalRepository.getAnimalProjectionById(animalId);
    }

    private static void throwIfTypesAlreadyExist(Animal animal, AnimalType animalType) {
        if (animal.getAnimalTypeList().contains(animalType)) {
            throw new AlreadyExistException("new animalType with id = " + animalType.getId() + " aldready exist");
        }
    }

    @Override
    @Transactional
    public AnimalProjection changeTypeAnimal(@Id Long animalId, @Valid TypesDto oldAndNewTypes) {
        Animal animal = getAnimalEntityById(animalId);
        AnimalType oldAnimalType = animalTypeService.getAnimalTypeEntityById(oldAndNewTypes.getOldTypeId());
        AnimalType newAnimalType = animalTypeService.getAnimalTypeEntityById(oldAndNewTypes.getNewTypeId());

        int oldTypeIndex = getOldTypeIndex(animal, oldAnimalType);

        if (oldTypeIndex == -1) {
            throw new NotFoundException("old type with id = " + oldAndNewTypes.getOldTypeId() + " doesnt exist animal");
        }

        throwIfTypesAlreadyExist(animal, newAnimalType);
        animal.getAnimalTypeList().set(oldTypeIndex, newAnimalType);
        flushAnimal();

        return animalRepository.getAnimalProjectionById(animalId);
    }

    private static int getOldTypeIndex(Animal animal, AnimalType oldAnimalType) {
        return animal.getAnimalTypeList().indexOf(oldAnimalType);
    }

    @Override
    @Transactional
    public AnimalProjection deleteTypeFromAnimal(@Id Long animalId, @Id Long typeId) {
        Animal animal = getAnimalEntityById(animalId);
        AnimalType animalType = animalTypeService.getAnimalTypeEntityById(typeId);

        if (animal.getAnimalTypeList().size() == 1 && animal.getAnimalTypeList().get(0).equals(animalType)) {
            throw new IncompatibleData();
        }

        int oldTypeIndex = getOldTypeIndex(animal, animalType);
        if (oldTypeIndex == -1) {
            throw new NotFoundException("animal havent type with id = " + typeId);
        }
        animal.getAnimalTypeList().get(oldTypeIndex).removeAnimal(animal);
        animal.getAnimalTypeList().remove(oldTypeIndex);

        animalRepository.save(animal);

        return animalRepository.getAnimalProjectionById(animalId);
    }

    @Override
    @Transactional
    public void deleteAnimal(Long id) {
        Animal animal = getAnimalEntityById(id);

        if (!animal.getAnimalLocations().isEmpty()) {
            throw new ConnectionWithAnimal("");
        }

        for (AnimalType animalType : animal.getAnimalTypeList()) {
            animalType.removeAnimal(animal);
        }

        animal.getAnimalTypeList().clear();

        animalRepository.delete(animal);
    }

    private static boolean setAliveForDeadAnimal(AnimalDto animalDto, Animal animal) {
        return animal.getLifeStatus().equals(LifeStatusEnum.DEAD.getLifeStatus())
                && animalDto.getLifeStatus().equals(LifeStatusEnum.ALIVE.getLifeStatus());
    }

    @Override
    @Transactional
    public List<AnimalProjection> getAnimalListByParams(@Valid AnimalParams animalDtoSpecification) {

        Pageable pageable = PageRequest.of(animalDtoSpecification.getFrom(), animalDtoSpecification.getSize(), Sort.by(Animal_.ID));

        Page<AnimalProjection> animalProjectionList = animalRepository.
                findAnimalProjectionByParams(animalDtoSpecification,animalDtoSpecification.getStartDateTime(), animalDtoSpecification.getStartDateTime(), pageable);

        return animalProjectionList.getContent();
    }

    @Override
    @Transactional
    public boolean animalExistById(@Id Long id) {
        return animalRepository.existsById(id);
    }

    @Override
    public void flushAnimal() {
        animalRepository.flush();
    }

    @Override
    @Transactional
    public Animal getAnimalEntityById(@Id Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal with id = " + id + " not found"));
    }

}
