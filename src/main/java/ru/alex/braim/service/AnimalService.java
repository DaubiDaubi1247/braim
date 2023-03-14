package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AnimalDto;
import ru.alex.braim.dto.Projection.AnimalProjection;
import ru.alex.braim.dto.OldAndNewTypes;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.requestParam.AnimalRequestParams;

import java.util.List;

public interface AnimalService {
    AnimalProjection getAnimalById(@Id Long id);

    Animal getAnimalEntityById(@Id Long id);

    AnimalProjection createAnimal(@Valid AnimalDto animalDto);

    AnimalProjection updateAnimal(@Valid AnimalDto animalDto, @Id Long id);

    AnimalProjection addTypeToAnimal(@Id Long animalId, @Id Long typeId);

    AnimalProjection changeTypeAnimal(@Id Long oldTypeId, @Valid OldAndNewTypes oldAndNewTypes);

    AnimalProjection deleteTypeFromAnimal(@Id Long animalId, @Id Long typeId);

    void deleteAnimal(@Id Long id);

    List<AnimalProjection> getAnimalListByParams(@Valid AnimalRequestParams animalDtoSpecification);

    boolean animalExistById(@Id Long id);

    void flushAnimal();
}
