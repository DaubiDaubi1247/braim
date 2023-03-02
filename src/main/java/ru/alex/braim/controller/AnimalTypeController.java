package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.AnimalTypeDto;
import ru.alex.braim.service.AnimalTypeService;

@RestController
@RequestMapping("/animals/types")
@RequiredArgsConstructor
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    @GetMapping("/types/{typeId}")
    public ResponseEntity<AnimalTypeDto> getAnimalTypeById(@PathVariable Long typeId) {
        return ResponseEntity.ok(animalTypeService.getAnimalTypeById(typeId));
    }

    @PostMapping
    public ResponseEntity<AnimalTypeDto> createType(@RequestBody AnimalTypeDto animalTypeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalTypeService.createType(animalTypeDto));
    }
}
