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

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<AnimalProjection> {

    @Query(" SELECT an " +
            "FROM Animal an " +
            "WHERE an.id = :id")
    AnimalProjection getAnimalProjectionById(@Param("id") Long id);

    @Query(" SELECT a, cil.chippingDateTime, cil.chipper.id AS chipId, cil.deathDateTime, " +
            "li.id AS locationId " +
            "FROM Animal a " +
            "JOIN a.chippingInfo cil " +
            "JOIN a.animalTypeList atl " +
            "JOIN a.locationList li " +
            "WHERE  (:#{#arp.startDateTime} IS null OR cil.chippingDateTime >= :#{#arp.startDateTime}) " +
            "   AND (:#{#arp.endDateTime} IS null OR :#{#arp.endDateTime} >= cil.chippingDateTime) " +
            "   AND (:#{#arp.chipperId} IS null OR cil.chipper.id = :#{#arp.chipperId}) " +
            "   AND (:#{#arp.chippingLocationId} IS null OR cil.id = :#{#arp.chippingLocationId}) " +
            "   AND (:#{#arp.lifeStatus} IS null OR a.lifeStatus = :#{#arp.lifeStatus}) " +
            "   AND (:#{#arp.gender} IS null OR a.gender = :#{#arp.gender}) ")
    Page<AnimalProjection> findAnimalProjectionByParams(@Param("arp") AnimalRequestParams animalRequestParams,
                                                          Pageable pageable);

}
