package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.braim.entity.LocationInfo;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long> {
}
