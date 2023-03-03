package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.AnimalDto;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.dto.OldAndNewTypes;
import ru.alex.braim.requestParam.AnimalRequestParams;
import ru.alex.braim.requestParam.DateRequestParams;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final LocationService locationService;

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

    @GetMapping("/{animalId}/locations")
    public ResponseEntity<List<LocationProjection>> getVisitedLocation(@PathVariable Long animalId,
                                                                 DateRequestParams dateRequestParams) {
        return ResponseEntity.ok(locationService.getLocationVisitedPointList(dateRequestParams, animalId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AnimalProjection>> getAnimalListByParams(
            AnimalRequestParams animalDtoSpecification) {

        return ResponseEntity.ok(animalService.getAnimalListByParams(animalDtoSpecification));
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalProjection> addTypeToAnimal(@PathVariable Long animalId,
                                                            @PathVariable Long typeId) {

        return ResponseEntity.ok(animalService.addTypeToAnimal(animalId, typeId));
    }

    @PutMapping("/{animalId}/types")
    public ResponseEntity<AnimalProjection> changeTypeToAnimal(@PathVariable Long animalId,
                                                               @RequestBody OldAndNewTypes oldAndNewTypes) {

        return ResponseEntity.ok(animalService.changeTypeAnimal(animalId, oldAndNewTypes));
    }
}
