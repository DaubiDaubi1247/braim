package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AnimalTypeDto;

public interface AnimalTypeService {
    AnimalTypeDto getAnimalTypeById(@Id Long id);

    AnimalTypeDto createType(@Valid AnimalTypeDto animalTypeDto);

    AnimalTypeDto updateType(@Valid AnimalTypeDto animalTypeDto, @Id Long id);

}
