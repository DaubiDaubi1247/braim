package ru.alex.braim.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.alex.braim.dto.AnimalDtoSpecification;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.entity.Animal_;
import ru.alex.braim.entity.ChippingInfo_;

import java.util.Date;

public class AnimalSpecification {
    private static Specification<AnimalProjection> greaterThanStartDate(Date startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root
                .get(String.valueOf(ChippingInfo_.chippingDateTime)), startDate);
    }

    private static Specification<AnimalProjection> lessThanEndDate(Date endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root
                .get(String.valueOf(ChippingInfo_.chippingDateTime)), endDate);
    }

    private static  <T extends Number>  Specification<AnimalProjection> equalsById(T id, String columnName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(columnName), id);
    }

    private static Specification<AnimalProjection> likeByString(String value, String columnName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(columnName), value);
    }

    public static Specification<AnimalProjection> getAnimalProjectionListByParameters(AnimalDtoSpecification animalDtoSpecification) {
        return Specification.where(greaterThanStartDate(animalDtoSpecification.getStartDateTime()))
                .and(lessThanEndDate(animalDtoSpecification.getEndDateTime()))
                .and(equalsById(animalDtoSpecification.getChipperId(), ChippingInfo_.chippingDateTime.toString()))
                .and(equalsById(animalDtoSpecification.getChippingLocationId(), ChippingInfo_.id.toString()))
                .and(likeByString(animalDtoSpecification.getLifeStatus(), Animal_.lifeStatus.toString()))
                .and(likeByString(animalDtoSpecification.getGender(), Animal_.gender.toString()));
    }
}
