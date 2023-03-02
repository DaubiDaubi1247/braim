package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AnimalTypeDto;
import ru.alex.braim.entity.AnimalType;
import ru.alex.braim.exception.AlreadyExistException;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AnimalTypeMapper;
import ru.alex.braim.repository.AnimalTypeRepository;
import ru.alex.braim.service.AnimalTypeService;

@Service
@RequiredArgsConstructor
@Validated
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalTypeMapper animalTypeMapper;
    @Override
    @Transactional
    public AnimalTypeDto getAnimalTypeById(@Id Long id) {
        return animalTypeMapper.toDto(getAnimalTypeEntityById(id));
    }

    @Override
    @Transactional
    public AnimalTypeDto createType(AnimalTypeDto animalTypeDto) {
        if (animalTypeRepository.existsByType(animalTypeDto.getType())) {
            throw new AlreadyExistException("type with name = " + animalTypeDto.getType() + " already exist");
        }

        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDto);

        return animalTypeMapper.toDto(animalTypeRepository.save(animalType));
    }

    private AnimalType getAnimalTypeEntityById(Long id) {
        return animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal type with id = " + id + " not found"));
    }
}
