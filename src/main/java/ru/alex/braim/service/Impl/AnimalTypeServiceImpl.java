package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alex.braim.dto.AnimalTypeDto;
import ru.alex.braim.service.AnimalTypeService;

@Service
@RequiredArgsConstructor
public class AnimalTypeServiceImpl implements AnimalTypeService {
    @Override
    public AnimalTypeDto getAnimalType(Long id) {
        return null;
    }
}
