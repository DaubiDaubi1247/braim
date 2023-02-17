package ru.alex.braim.service;

import ru.alex.braim.dto.AnimalProjection;

public interface AnimalService {
    AnimalProjection getAnimalById(Long id);
}
