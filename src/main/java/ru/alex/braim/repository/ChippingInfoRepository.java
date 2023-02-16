package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.braim.entity.ChippingInfo;

import java.util.Optional;

@Repository
public interface ChippingInfoRepository extends JpaRepository<ChippingInfo, Long> {

    @Query(" SELECT ci " +
            "FROM ChippingInfo ci " +
            "JOIN ci.animalList al " +
            "WHERE al.id = :id")
    Optional<ChippingInfo> findChippingInfoByAnimalId(Long id);

}
