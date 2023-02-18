package ru.alex.braim.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LocationInfoDto {
    private Long id;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
