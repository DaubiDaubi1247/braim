package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.braim.dto.AnimalResponseDto;
import ru.alex.braim.dto.ChippingInfoDto;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AnimalMapper;
import ru.alex.braim.mapper.ListMapper;
import ru.alex.braim.repository.AnimalRepository;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.service.ChippingInfoService;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ChippingInfoService chippingInfoService;
    private final AnimalMapper animalMapper;
    private final ListMapper listMapper;

    @Override
    @Transactional
    public AnimalResponseDto getAnimalById(Long id) {
        Animal animal = getAnimalEntityById(id);
        ChippingInfoDto chippingInfoDto = chippingInfoService.getChippingInfoByAnimalId(id);

        AnimalResponseDto animalResponseDto = animalMapper.toResponseDto(animal, chippingInfoDto);
        animalResponseDto.setChippingLocationId(getLastChippingId(animal));
        animalResponseDto.setVisitedLocations(listMapper.toLongList(animal.getChippingInfoList()));

        return animalMapper.toResponseDto(animal, chippingInfoDto);
    }

    private Animal getAnimalEntityById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal with id = " + id + " not found"));
    }

    private Integer getLastChippingId(Animal animal) {
        return Math.toIntExact(animal.getChippingInfoList().get(animal.getChippingInfoList().size() - 1).getId());
    }
}
