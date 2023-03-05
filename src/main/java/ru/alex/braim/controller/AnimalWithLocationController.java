package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.LocationProjection;
import ru.alex.braim.requestParam.DateRequestParams;
import ru.alex.braim.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalWithLocationController {

    private final LocationService locationService;

    @GetMapping("/{animalId}/locations")
    public ResponseEntity<List<LocationProjection>> getVisitedLocation(@PathVariable Long animalId,
                                                                       DateRequestParams dateRequestParams) {
        return ResponseEntity.ok(locationService.getLocationVisitedPointList(dateRequestParams, animalId));
    }

    @PostMapping("/{animalId}/locations/{pointId}")
    public ResponseEntity<LocationProjection> addLocationPointToAnimal(@PathVariable Long animalId,
                                                                      @PathVariable Long pointId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.addLocationToAnimal(animalId, pointId));
    }
}
