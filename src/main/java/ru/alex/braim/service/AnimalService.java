package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.dto.AnimalDtoSpecification;
import ru.alex.braim.dto.AnimalProjection;

import java.util.List;

public interface AnimalService {
    AnimalProjection getAnimalById(Long id);

    List<AnimalProjection> getAnimalListByParams(@Valid AnimalDtoSpecification animalDtoSpecification);
}
