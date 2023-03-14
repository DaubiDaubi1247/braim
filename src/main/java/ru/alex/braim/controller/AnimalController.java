package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.AnimalDto;
import ru.alex.braim.dto.Projection.AnimalProjection;
import ru.alex.braim.requestParam.AnimalParams;
import ru.alex.braim.service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalProjection> createAnimal(@RequestBody AnimalDto animalDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.createAnimal(animalDto));
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<AnimalProjection> updateAnimal(@PathVariable Long animalId,
                                                         @RequestBody AnimalDto animalDto) {

        return ResponseEntity.ok(animalService.updateAnimal(animalDto, animalId));
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long animalId) {

        animalService.deleteAnimal(animalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalProjection> getAnimalById(@PathVariable Long animalId) {
        return ResponseEntity.ok(animalService.getAnimalById(animalId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AnimalProjection>> getAnimalListByParams(
            AnimalParams animalDtoSpecification) {

        return ResponseEntity.ok(animalService.getAnimalListByParams(animalDtoSpecification));
    }
}
