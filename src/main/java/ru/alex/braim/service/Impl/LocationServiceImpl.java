package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.dto.LocationInfoDto;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.entity.LocationInfo;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.LocationInfoMapper;
import ru.alex.braim.repository.LocationInfoRepository;
import ru.alex.braim.requestParam.DateRequestParams;
import ru.alex.braim.service.LocationService;
import ru.alex.braim.specification.LocationSpecification;
import ru.alex.braim.utils.ListUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class LocationServiceImpl implements LocationService {

    private final LocationInfoRepository locationInfoRepository;
    private final LocationInfoMapper locationInfoMapper;

    @Override
    @Transactional
    public LocationInfoDto getLocationById(Long id) {
        return locationInfoMapper.toDto(getLocationInfoEntityById(id));
    }

    @Override
    @Transactional
    public List<LocationProjection> getLocationVisitedPointList(@Valid DateRequestParams dateRequestParams, Long id) {
        List<LocationProjection> locationProjectionList =  locationInfoRepository.findAll(LocationSpecification.getLocationPointByParams(dateRequestParams, id));

        return ListUtils.skipAndGetElements(locationProjectionList, dateRequestParams.getFrom(), dateRequestParams.getSize());
    }

    private LocationInfo getLocationInfoEntityById(Long id) {
        return locationInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("location with id = " + id + " not found"));
    }
}
