package ru.alex.braim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.LocationInfo;

import java.util.Date;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long>,
        JpaSpecificationExecutor<LocationProjection> {

    @Query(" SELECT li.id, ci.chippingDateTime, ci.id " +
            "FROM Animal a " +
            "JOIN a.chippingInfoList ci " +
            "JOIN a.locationList li " +
            "WHERE a.id = :animalId " +
            "   AND (CAST(:startDate AS timestamp)  IS null OR ci.chippingDateTime >= :startDate) " +
            "   AND (CAST(:endDate AS timestamp) IS null OR :endDate >= ci.chippingDateTime) ")
    Page<LocationProjection> findLocationPointByParams(@Param("startDate") Date startDate,
                                                       @Param("endDate") Date endDate,
                                                       @Param("animalId") Long animalId,
                                                       Pageable pageable);

    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);
}
