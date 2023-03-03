package ru.alex.braim.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alex.braim.dto.AnimalDto;
import ru.alex.braim.entity.Animal;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(target = "gender.name", source = "animalDto.gender")
    Animal toEntity(AnimalDto animalDto);
}
