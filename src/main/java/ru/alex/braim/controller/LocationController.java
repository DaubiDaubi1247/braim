package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.braim.dto.LocationInfoDto;
import ru.alex.braim.service.LocationService;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("{pointId}")
    public ResponseEntity<LocationInfoDto> getLocationInfoById(@PathVariable Long pointId) {
        return ResponseEntity.ok(locationService.getLocationById(pointId));
    }
}
