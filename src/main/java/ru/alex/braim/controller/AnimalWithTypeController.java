package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.Projection.AnimalProjection;
import ru.alex.braim.dto.OldAndNewTypes;
import ru.alex.braim.service.AnimalService;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalWithTypeController {

    private final AnimalService animalService;

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalProjection> addTypeToAnimal(@PathVariable Long animalId,
                                                            @PathVariable Long typeId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.addTypeToAnimal(animalId, typeId));
    }

    @PutMapping("/{animalId}/types")
    public ResponseEntity<AnimalProjection> changeTypeToAnimal(@PathVariable Long animalId,
                                                               @RequestBody OldAndNewTypes oldAndNewTypes) {

        return ResponseEntity.ok(animalService.changeTypeAnimal(animalId, oldAndNewTypes));
    }

    @DeleteMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalProjection> deleteTypeFromAnimal(@PathVariable Long animalId,
                                                                 @PathVariable Long typeId) {

        return ResponseEntity.ok(animalService.deleteTypeFromAnimal(animalId, typeId));
    }
}
