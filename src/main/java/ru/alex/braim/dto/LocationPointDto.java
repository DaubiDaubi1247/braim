package ru.alex.braim.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alex.braim.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationPointDto {

    @Id
    private Long visitedLocationPointId;// Идентификатор объекта с информацией о посещенной точке локации

    @Id
    private Long locationPointId;
}
