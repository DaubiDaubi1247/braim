package ru.alex.braim.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.alex.braim.entity.AnimalLocation;
import ru.alex.braim.entity.AnimalLocation_;
import ru.alex.braim.entity.Animal_;
import ru.alex.braim.requestParam.DateRequestParams;

import java.util.Date;

public class AnimalLocationSpecification {
    private static Specification<AnimalLocation> greaterThanByDate(Date date) {
        if (date == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.
                greaterThanOrEqualTo(root.get(AnimalLocation_.visitedDate), date);
    }

    private static Specification<AnimalLocation> lessThanByDate(Date date) {
        if (date == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.
                lessThanOrEqualTo(root.get(AnimalLocation_.visitedDate), date);
    }

    private static Specification<AnimalLocation> equalsByAnimal(Long animalId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(AnimalLocation_.animal).get(Animal_.id), animalId);
    }

    public static Specification<AnimalLocation> locationPointsByDate(DateRequestParams dates, Long animalId) {
        return Specification.where(equalsByAnimal(animalId))
                .and(greaterThanByDate(dates.getStartDateTime()))
                .and(lessThanByDate(dates.getEndDateTime()));
    }
}
