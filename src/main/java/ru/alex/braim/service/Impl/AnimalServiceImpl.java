package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.braim.dto.AnimalDtoSpecification;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AnimalMapper;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.repository.AnimalRepository;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.specification.AnimalSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Override
    @Transactional
    public AnimalProjection getAnimalById(Long id) {

        return animalRepository.getAnimalProjectionById(id);
    }

    @Override
    @Transactional
    public List<AnimalProjection> getAnimalListByParams(AnimalDtoSpecification animalDtoSpecification) {
        return animalRepository.findAll(AnimalSpecification.getAnimalProjectionListByParameters(animalDtoSpecification));
    }

    private Animal getAnimalEntityById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal with id = " + id + " not found"));
    }
}
