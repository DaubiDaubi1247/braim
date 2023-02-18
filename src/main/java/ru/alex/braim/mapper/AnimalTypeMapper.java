package ru.alex.braim.mapper;

import org.mapstruct.Mapper;
import ru.alex.braim.dto.AnimalTypeDto;
import ru.alex.braim.entity.AnimalType;

@Mapper(componentModel = "spring")
public interface AnimalTypeMapper {

    AnimalTypeDto toDto(AnimalType animalType);
}
