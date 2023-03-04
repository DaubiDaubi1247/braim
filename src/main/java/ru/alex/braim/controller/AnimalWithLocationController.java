package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
