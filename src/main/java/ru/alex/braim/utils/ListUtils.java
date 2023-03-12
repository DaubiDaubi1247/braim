package ru.alex.braim.utils;

import org.springframework.stereotype.Component;
import ru.alex.braim.utils.interfaces.Identifiable;

import java.util.List;
import java.util.Objects;

@Component
public class ListUtils {
    public static List<Long> toLongList(List<? extends Identifiable> chippingInfoList) {

        return chippingInfoList.stream()
                .map(Identifiable::getId)
                .toList();
    }

    public static<T extends Identifiable> int indexOfById(List<T> a, Long id)
    {
        int index = 0;
        for (;index < a.size() ; index++) {
            if (Objects.equals(a.get(index).getId(), id)) {
                break;
            }
        }

        return index != a.size() ? index : -1;
    }
}
