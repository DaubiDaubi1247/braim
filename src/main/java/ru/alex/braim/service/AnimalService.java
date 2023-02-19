package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.requestParam.AnimalRequestParams;

import java.util.List;

public interface AnimalService {
    AnimalProjection getAnimalById(@Id Long id);

    List<AnimalProjection> getAnimalListByParams(@Valid AnimalRequestParams animalDtoSpecification);

    boolean animalExistById(@Id Long id);
}
