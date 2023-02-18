package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.dto.AnimalDtoSpecification;
import ru.alex.braim.entity.Animal;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AnimalMapper;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.repository.AnimalRepository;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.specification.AnimalSpecification;
import ru.alex.braim.utils.ListUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
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
    public List<AnimalProjection> getAnimalListByParams(@Valid AnimalDtoSpecification animalDtoSpecification) {
        List<AnimalProjection> animalProjectionList = animalRepository.findAll(AnimalSpecification.getAnimalProjectionListByParameters(animalDtoSpecification),
                Sort.by(Sort.Direction.ASC, "id"));

        return ListUtils.skipAndGetElements(animalProjectionList, animalDtoSpecification.getFrom(), animalDtoSpecification.getSize());
    }

    private Animal getAnimalEntityById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal with id = " + id + " not found"));
    }
}
