package ru.alex.braim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alex.braim.dto.Projection.LocationProjection;
import ru.alex.braim.entity.LocationInfo;

import java.time.OffsetDateTime;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long> {

    @Query(" SELECT a " +
            "FROM AnimalLocation a " +
            "WHERE a.animal.id = :animalId " +
            "   AND (CAST(:startDate AS timestamp ) IS null OR a.visitedDate >= CAST(:startDate AS timestamp )) " +
            "   AND (CAST(:endDate AS timestamp ) IS null OR CAST(:endDate AS timestamp ) >= a.visitedDate) ")
    Page<LocationProjection> findLocationPointByParams(@Param("startDate") OffsetDateTime startDate,
                                                       @Param("endDate") OffsetDateTime endDate,
                                                       @Param("animalId") Long animalId,
                                                       Pageable pageable);

    @Query(" SELECT a " +
            "FROM AnimalLocation a " +
            "WHERE a.id = :animalLocationId")
    LocationProjection findLocationProjectionByAnimalId(@Param("animalLocationId") Long animalLocationId);

    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);
}
