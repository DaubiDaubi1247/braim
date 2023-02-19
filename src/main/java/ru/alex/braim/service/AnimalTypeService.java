package ru.alex.braim.service;

import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AnimalTypeDto;

public interface AnimalTypeService {
    AnimalTypeDto getAnimalTypeById(@Id Long id);

}
