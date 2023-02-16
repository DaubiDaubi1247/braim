package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.braim.dto.ChippingInfoDto;
import ru.alex.braim.entity.ChippingInfo;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.ChippingInfoMapper;
import ru.alex.braim.repository.ChippingInfoRepository;
import ru.alex.braim.service.ChippingInfoService;

@Service
@RequiredArgsConstructor
public class ChippingInfoServiceImpl implements ChippingInfoService {

    private final ChippingInfoRepository chippingInfoRepository;
    private final ChippingInfoMapper chippingInfoMapper;

    @Override
    @Transactional
    public ChippingInfoDto getChippingInfoByAnimalId(Long id) {
        ChippingInfo chippingInfo = getChippingInfoEntityByAnimalId(id);

        return chippingInfoMapper.toDto(chippingInfo);
    }

    private ChippingInfo getChippingInfoEntityByAnimalId(Long id) {
       return chippingInfoRepository.findChippingInfoByAnimalId(id)
                .orElseThrow(() -> new NotFoundException("information about animal with id = " + id + " not found"));
    }
}
