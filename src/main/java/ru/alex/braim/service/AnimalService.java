package ru.alex.braim.service;

import ru.alex.braim.dto.AnimalResponseDto;

public interface AnimalService {
    AnimalResponseDto getAnimalById(Long id);
}
