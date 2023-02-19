package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.dto.AnimalTypeDto;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.requestParam.AnimalRequestParams;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.service.AnimalTypeService;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalTypeService animalTypeService;

    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalProjection> getAnimalById(@PathVariable Long animalId) {
        return ResponseEntity.ok(animalService.getAnimalById(animalId));
    }

    @GetMapping(" /animals/{animalId}/locations")
    public ResponseEntity<LocationProjection> getVisitedLocation(@PathVariable Long animalId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping(" /search")
    public ResponseEntity<List<AnimalProjection>> getAnimalListByParams(AnimalRequestParams animalDtoSpecification) {
        return ResponseEntity.ok(animalService.getAnimalListByParams(animalDtoSpecification));
    }

    @GetMapping("/types/{typeId}")
    public ResponseEntity<AnimalTypeDto> getAnimalTypeById(@PathVariable Long typeId) {
        return ResponseEntity.ok(animalTypeService.getAnimalTypeById(typeId));
    }
}
