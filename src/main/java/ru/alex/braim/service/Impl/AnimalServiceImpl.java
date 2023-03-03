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
import ru.alex.braim.dto.AnimalDto;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.entity.*;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AnimalMapper;
import ru.alex.braim.repository.AnimalRepository;
import ru.alex.braim.requestParam.AnimalRequestParams;
import ru.alex.braim.service.AccountService;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.service.AnimalTypeService;
import ru.alex.braim.service.LocationService;
import ru.alex.braim.utils.enums.LifeStatusEnum;

import java.util.List;

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
    public AnimalProjection createAnimal(AnimalDto animalDto) {
        List<AnimalType> animalType = animalTypeService.getAnimalTypeList(animalDto.getAnimalsTypes());
        Account account = accountService.getAccountEntityById(Long.valueOf(animalDto.getChipperId()));
        LocationInfo locationInfo = locationService.getLocationEntityById(animalDto.getChippingLocationId());

        Animal newAnimal = animalMapper.toEntity(animalDto);
        ChippingInfo chippingInfo = new ChippingInfo();
        chippingInfo.setChipper(account);

        newAnimal.setAnimalTypeList(animalType);
        newAnimal.setLifeStatus(LifeStatusEnum.ALIVE.getLifeStatus());
        newAnimal.getLocationList().add(locationInfo);
        newAnimal.setChippingInfo(chippingInfo);

        Animal savedAnimal = animalRepository.save(newAnimal);

        return animalRepository.getAnimalProjectionById(savedAnimal.getId());
    }

    @Override
    @Transactional
    public List<AnimalProjection> getAnimalListByParams(@Valid AnimalRequestParams animalDtoSpecification) {

        Pageable pageable = PageRequest.of(animalDtoSpecification.getFrom(), animalDtoSpecification.getSize());

        Page<AnimalProjection> animalProjectionList = animalRepository.
                findAnimalProjectionByParams(animalDtoSpecification, pageable);

        return animalProjectionList.getContent();
    }

    @Override
    @Transactional
    public boolean animalExistById(@Id Long id) {
        return animalRepository.existsById(id);
    }

    private Animal getAnimalEntityById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal with id = " + id + " not found"));
    }

}
