package ru.alex.braim.service;

import ru.alex.braim.projection.AnimalProjection;

public interface AnimalService {
    AnimalProjection getAnimalById(Long id);
}
