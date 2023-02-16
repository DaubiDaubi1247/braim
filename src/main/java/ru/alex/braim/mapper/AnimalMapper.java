package ru.alex.braim.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alex.braim.dto.AnimalResponseDto;
import ru.alex.braim.dto.ChippingInfoDto;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.entity.ChippingInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(source = "animal.gender.name", target = "gender")
    AnimalResponseDto toResponseDto(Animal animal, ChippingInfoDto chippingInfoDto);

    default List<Long> toVisitedLocationIdList(List<ChippingInfo> chippingInfoList) {
        return chippingInfoList.stream()
                .map(ChippingInfo::getId)
                .collect(Collectors.toList());
    }
}
