package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.LocationInfoDto;
import ru.alex.braim.service.LocationService;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{pointId}")
    public ResponseEntity<LocationInfoDto> getLocationInfoById(@PathVariable Long pointId) {
        return ResponseEntity.ok(locationService.getLocationById(pointId));
    }

    @PostMapping
    public ResponseEntity<LocationInfoDto> createLocation(@RequestBody LocationInfoDto locationInfoDto) {
        return null;
    }
}
