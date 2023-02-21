package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.LocationInfo;

import java.util.Date;
import java.util.List;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long>,
        JpaSpecificationExecutor<LocationProjection> {

    @Query(" SELECT li.id, ci.chippingDateTime, ci.id " +
            "FROM Animal a " +
            "JOIN a.chippingInfoList ci " +
            "JOIN a.locationList li")
    List<LocationProjection> findLocationPointByParams(Date startDate, Date endDate, Long animalId);
}
