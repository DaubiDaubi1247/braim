package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.LocationInfo;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long>, JpaSpecificationExecutor<LocationProjection> {
}
