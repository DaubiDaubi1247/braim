package ru.alex.braim.service;

import ru.alex.braim.dto.ChippingInfoDto;

public interface ChippingInfoService {

    ChippingInfoDto getChippingInfoByAnimalId(Long id);
}
