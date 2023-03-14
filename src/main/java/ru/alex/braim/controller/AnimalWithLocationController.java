package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.LocationPointDto;
import ru.alex.braim.dto.Projection.LocationProjection;
import ru.alex.braim.requestParam.DateRequestParams;
import ru.alex.braim.service.AnimalWithLocationService;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalWithLocationController {

    private final AnimalWithLocationService animalWithLocationService;

    @GetMapping("/{animalId}/locations")
    public ResponseEntity<List<LocationProjection>> getVisitedLocation(@PathVariable Long animalId,
                                                                       DateRequestParams dateRequestParams) {
        return ResponseEntity.ok(animalWithLocationService.getLocationVisitedPointList(dateRequestParams, animalId));
    }

    @PostMapping("/{animalId}/locations/{pointId}")
    public ResponseEntity<LocationProjection> addLocationPointToAnimal(@PathVariable Long animalId,
                                                                      @PathVariable Long pointId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(animalWithLocationService.addLocationToAnimal(animalId, pointId));
    }

    @PutMapping("/{animalId}/locations")
    public ResponseEntity<LocationProjection> updateVisitedLocation(@PathVariable Long animalId,
                                                                    @RequestBody LocationPointDto locationInfoDto) {

        return ResponseEntity.ok(animalWithLocationService.updateLocationPoint(animalId, locationInfoDto));
    }

    @DeleteMapping("/{animalId}/locations/{visitedPointId}")
    public ResponseEntity<Void> deleteVisitedLocationFromAnimal(@PathVariable Long animalId,
                                                                @PathVariable Long visitedPointId) {

        animalWithLocationService.deleteLocationPointFromAnimal(animalId, visitedPointId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
