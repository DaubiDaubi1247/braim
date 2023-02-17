package ru.alex.braim.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.entity.ChippingInfo_;

import java.util.Date;

public class AnimalSpecification {
    private Specification<AnimalProjection> greaterThanStartDate(Date startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root
                .get(String.valueOf(ChippingInfo_.chippingDateTime)), startDate);
    }

    private Specification<AnimalProjection> lessThanStartDate(Date endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root
                .get(String.valueOf(ChippingInfo_.chippingDateTime)), endDate);
    }

    private <T extends Number>  Specification<AnimalProjection> equalsById(T id, String columnName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(columnName), id);
    }

    private Specification<AnimalProjection> likeByString(String value, String columnName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(columnName), value);
    }

    public Specification<AnimalProjection> getAnimalProjectionListByParameters() {
        return Specification.where(greaterThanStartDate(new Date()));
    }
}
