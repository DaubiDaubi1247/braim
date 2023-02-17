package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.dto.AnimalProjection;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    @Query(" SELECT an, chil, atl.type " +
            "FROM Animal an " +
            "JOIN an.chippingInfoList chil " +
            "JOIN an.animalTypesList atl " +
            "WHERE an.id = ?1")
    AnimalProjection getAnimalProjectionById(Long id);
}
