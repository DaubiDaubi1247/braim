package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alex.braim.entity.ChippingInfo;
import ru.alex.braim.repository.ChippingInfoRepository;
import ru.alex.braim.service.ChippingInfoService;

@Service
@RequiredArgsConstructor
public class ChippingInfoServiceImpl implements ChippingInfoService {

    private final ChippingInfoRepository chippingInfoRepository;

    @Override
    public ChippingInfo saveChippingInfo(ChippingInfo chippingInfo) {
        return chippingInfoRepository.save(chippingInfo);
    }
}
