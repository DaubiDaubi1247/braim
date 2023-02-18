package ru.alex.braim.utils;

import org.springframework.stereotype.Component;
import ru.alex.braim.utils.interfaces.Identifiable;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListUtils {
    public static List<Long> toLongList(List<? extends Identifiable> chippingInfoList) {
        return chippingInfoList.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toList());
    }

    public static <T> List<T> skipAndGetElements(List<T> list, Integer from, Integer size) {
        return list.stream()
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }
}
