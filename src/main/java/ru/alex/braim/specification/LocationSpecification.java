package ru.alex.braim.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.entity.Animal_;
import ru.alex.braim.entity.ChippingInfo_;
import ru.alex.braim.entity.LocationInfo;
import ru.alex.braim.requestParam.DateRequestParams;

import java.util.Date;

public class LocationSpecification {
    private static Specification<LocationProjection> greaterThanStartDate(Date startDate) {
        if (startDate == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root
                .get(String.valueOf(ChippingInfo_.chippingDateTime)), startDate);
    }

    private static Specification<LocationProjection> lessThanEndDate(Date endDate) {
        if (endDate == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root
                .get(String.valueOf(ChippingInfo_.chippingDateTime)), endDate);
    }

    private static Specification<LocationProjection> equalsByAnimalId(Long id) {
        if (id == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {
            Join<Animal, LocationInfo> animalLocation = root.join(String.valueOf(Animal_.locationList));
            return criteriaBuilder.equal(animalLocation.get(String.valueOf(Animal_.id)), id);
        };
    }

    public static Specification<LocationProjection> getLocationPointByParams(DateRequestParams dateRequestParams, Long id) {
        return Specification.where(equalsByAnimalId(id))
                .and(greaterThanStartDate(dateRequestParams.getStartDateTime()))
                .and(lessThanEndDate(dateRequestParams.getEndDateTime()));
    }
}
