package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.AnimalProjection;
import ru.alex.braim.dto.AnimalTypeDto;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.requestParam.AnimalRequestParams;
import ru.alex.braim.requestParam.DateRequestParams;
import ru.alex.braim.service.AnimalService;
import ru.alex.braim.service.AnimalTypeService;
import ru.alex.braim.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalTypeService animalTypeService;
    private final LocationService locationService;

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

    @GetMapping("/types/{typeId}")
    public ResponseEntity<AnimalTypeDto> getAnimalTypeById(@PathVariable Long typeId) {
        return ResponseEntity.ok(animalTypeService.getAnimalTypeById(typeId));
    }
}
