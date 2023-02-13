package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alex.braim.dto.AnimalResponseDto;
import ru.alex.braim.repository.AnimalRepository;
import ru.alex.braim.service.AnimalService;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    @Override
    public AnimalResponseDto getAnimalById(Long id) {
        return null;
    }
}
