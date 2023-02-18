package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.braim.dto.AnimalTypeDto;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AnimalTypeMapper;
import ru.alex.braim.repository.AnimalTypeRepository;
import ru.alex.braim.service.AnimalTypeService;

@Service
@RequiredArgsConstructor
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalTypeMapper animalTypeMapper;
    @Override
    @Transactional
    public AnimalTypeDto getAnimalTypeById(Long id) {
        return animalTypeMapper.toDto(animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal type with id = " + id + " not found")));
    }
}
