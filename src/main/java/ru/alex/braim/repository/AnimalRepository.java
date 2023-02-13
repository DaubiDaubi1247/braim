package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex.braim.entity.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
