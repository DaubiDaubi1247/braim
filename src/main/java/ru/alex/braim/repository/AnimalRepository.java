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
    @Query(" SELECT an, chil, atl.type " +
            "FROM Animal an " +
            "JOIN an.chippingInfoList chil " +
            "JOIN an.animalTypeList atl " +
            "WHERE an.id = ?1")
    AnimalProjection getAnimalProjectionById(Long id);

    @Query(" SELECT a, cil.chippingDateTime, cil.chipper.id, cil.deathDateTime," +
            "li.id " +
            "FROM Animal a " +
            "JOIN a.gender gen " +
            "JOIN a.lifeStatus ls " +
            "JOIN a.chippingInfoList cil " +
            "JOIN a.animalTypeList atl " +
            "JOIN a.locationList li " +
            "WHERE (CAST(:startDate AS timestamp)  IS null OR cil.chippingDateTime >= :#{arp.startDateTime}) " +
            "   AND (CAST(:endDate AS timestamp) IS null OR :#{arp.endDateTime} >= cil.chippingDateTime) " +
            "   AND (:#{arp.chipperId} IS null OR cil.chipper.id = :#{arp.chipperId}) " +
            "   AND (:#{arp.chippingLocationId} IS null OR cil.id = :#{arp.chippingLocationId}) " +
            "   AND (:#{arp.lifeStatus} IS null OR a.lifeStatus = :#{arp.lifeStatus}) " +
            "   AND (:#{arp.gender} IS null OR a.gender.name = :#{arp.gender}) ")
    Page<AnimalProjection> getAnimalProjectionByParams(@Param("arp") AnimalRequestParams animalRequestParams, Pageable pageable);

}
