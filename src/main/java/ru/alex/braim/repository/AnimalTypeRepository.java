package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.braim.entity.AnimalType;

import java.util.Optional;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

    Optional<AnimalType> findByType(String type);

    boolean existsByType(String type);
}
