package ru.alex.braim.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alex.braim.dto.AnimalResponseDto;
import ru.alex.braim.dto.ChippingInfoDto;
import ru.alex.braim.entity.Animal;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(source = "animal.gender.name", target = "gender")
    AnimalResponseDto toResponseDto(Animal animal, ChippingInfoDto chippingInfoDto);
}
