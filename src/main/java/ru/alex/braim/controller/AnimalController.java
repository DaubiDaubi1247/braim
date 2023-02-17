package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.braim.dto.AnimalDtoSpecification;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalProjection> getAnimalById(@PathVariable Long animalId) {
        return ResponseEntity.ok(animalService.getAnimalById(animalId));
    }

    @GetMapping(" /animals/search")
    public ResponseEntity<List<AnimalProjection>> getAnimalListByParams(AnimalDtoSpecification animalDtoSpecification) {
        return ResponseEntity.ok(animalService.getAnimalListByParams(animalDtoSpecification));
    }
}
