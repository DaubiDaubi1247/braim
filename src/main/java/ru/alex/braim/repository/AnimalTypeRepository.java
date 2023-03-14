package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.braim.entity.AnimalType;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
    boolean existsByType(String type);
}
