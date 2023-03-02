package ru.alex.braim.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alex.braim.dto.LocationInfoDto;
import ru.alex.braim.entity.LocationInfo;

@Mapper(componentModel = "spring")
public interface LocationInfoMapper {
    LocationInfoDto toDto(LocationInfo locationInfo);

    @Mapping(target = "id", ignore = true)
    LocationInfo toEntity(LocationInfoDto locationInfoDto);
}
