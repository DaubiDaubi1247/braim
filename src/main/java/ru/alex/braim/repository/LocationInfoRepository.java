package ru.alex.braim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.AnimalLocation;
import ru.alex.braim.entity.LocationInfo;

import java.util.Date;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long>,
        JpaSpecificationExecutor<AnimalLocation> {

    @Query(" SELECT li.id, ci.chippingDateTime, ci.id " +
            "FROM Animal a " +
            "JOIN a.chippingInfo ci " +
            "JOIN a.animalLocations li " +
            "WHERE a.id = :animalId " +
            "   AND (CAST(:startDate AS date ) IS null OR ci.chippingDateTime >= :startDate) " +
            "   AND (CAST(:endDate AS date ) IS null OR :endDate >= ci.chippingDateTime) ")
    Page<LocationProjection> findLocationPointByParams(@Param("startDate") Date startDate,
                                                       @Param("endDate") Date endDate,
                                                       @Param("animalId") Long animalId,
                                                       Pageable pageable);

    @Query(" SELECT a " +
            "FROM AnimalLocation a " +
            "WHERE a.id = :animalLocationId")
    LocationProjection findLocationProjectionByAnimalId(@Param("animalLocationId") Long animalLocationId);

    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);
}
