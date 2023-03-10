package ru.alex.braim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.LocationInfo;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long> {

    @Query(" SELECT a " +
            "FROM AnimalLocation a " +
            "WHERE a.animal.id = :animalId " +
            "   AND (CAST(:startDate AS date ) IS null OR a.visitedDate >= :startDate) " +
            "   AND (CAST(:endDate AS date ) IS null OR :endDate >= a.visitedDate) ")
    Page<LocationProjection> findLocationPointByParams(@Param("startDate") java.sql.Date startDate,
                                                       @Param("endDate") java.sql.Date endDate,
                                                       @Param("animalId") Long animalId,
                                                       Pageable pageable);

    @Query(" SELECT a " +
            "FROM AnimalLocation a " +
            "WHERE a.id = :animalLocationId")
    LocationProjection findLocationProjectionByAnimalId(@Param("animalLocationId") Long animalLocationId);

    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);
}
