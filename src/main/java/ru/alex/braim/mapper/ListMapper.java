package ru.alex.braim.mapper;

import org.mapstruct.Mapper;
import ru.alex.braim.utils.interfaces.Identifiable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ListMapper {
    default List<Long> toLongList(List<? extends Identifiable> chippingInfoList) {
        return chippingInfoList.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toList());
    }
}
