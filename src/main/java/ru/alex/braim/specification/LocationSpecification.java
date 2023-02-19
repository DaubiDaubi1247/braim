package ru.alex.braim.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.Animal_;
import ru.alex.braim.entity.ChippingInfo_;
import ru.alex.braim.requestParam.DateRequestParams;

import java.util.Date;

public class LocationSpecification {
    private static Specification<LocationProjection> greaterThanStartDate(Date startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root
                .get(String.valueOf(ChippingInfo_.chippingDateTime)), startDate);
    }

    private static Specification<LocationProjection> lessThanEndDate(Date endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root
                .get(String.valueOf(ChippingInfo_.chippingDateTime)), endDate);
    }

    private static Specification<LocationProjection> equalsByAnimalId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(String.valueOf(Animal_.id)), id);
    }

    public static Specification<LocationProjection> getLocationPointByParams(DateRequestParams dateRequestParams, Long id) {
        return Specification.where(equalsByAnimalId(id))
                .and(greaterThanStartDate(dateRequestParams.getStartDateTime()))
                .and(lessThanEndDate(dateRequestParams.getEndDateTime()));
    }
}
