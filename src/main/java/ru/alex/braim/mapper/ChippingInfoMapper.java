package ru.alex.braim.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alex.braim.dto.ChippingInfoDto;
import ru.alex.braim.entity.ChippingInfo;

@Mapper(componentModel = "spring")
public interface ChippingInfoMapper {

    @Mapping(source = "chippingInfo.chipper.id", target = "chipperId")
    @Mapping(target = "chippingLocationId", ignore = true)
    @Mapping(target = "visitedLocations", ignore = true)
    ChippingInfoDto toDto(ChippingInfo chippingInfo);
}
