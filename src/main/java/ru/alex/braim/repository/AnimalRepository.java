package ru.alex.braim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.requestParam.AnimalRequestParams;

import java.time.OffsetDateTime;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<AnimalProjection> {

    @Query(" SELECT an " +
            "FROM Animal an " +
            "WHERE an.id = :id")
    AnimalProjection getAnimalProjectionById(@Param("id") Long id);

    @Query(" SELECT a " +
            "FROM Animal a " +
            "WHERE  (Cast(:startDate as timestamp) IS null OR a.chippingInfo.chippingDateTime >= :startDate) " +
            "   AND (Cast(:endDate as timestamp) IS null OR :endDate >= a.chippingInfo.chippingDateTime) " +
            "   AND (:#{#arp.chipperId} IS null OR a.chippingInfo.chipper.id = :#{#arp.chipperId}) " +
            "   AND (:#{#arp.chippingLocationId} IS null OR a.chippingInfo.id = :#{#arp.chippingLocationId}) " +
            "   AND (:#{#arp.lifeStatus} IS null OR a.lifeStatus = :#{#arp.lifeStatus}) " +
            "   AND (:#{#arp.gender} IS null OR a.gender = :#{#arp.gender}) ")
    Page<AnimalProjection> findAnimalProjectionByParams(@Param("arp") AnimalRequestParams animalRequestParams,
                                                          @Param("startDate") OffsetDateTime startDate,
                                                          @Param("endDate") OffsetDateTime endDate,
                                                          Pageable pageable);

}
