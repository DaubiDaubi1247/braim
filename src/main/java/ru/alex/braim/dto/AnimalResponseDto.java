package ru.alex.braim.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AnimalResponseDto extends AnimalDto {
    @Temporal(TemporalType.TIMESTAMP)
    private Date chippingDateTime;

    private Integer chipperId;

    private Integer chippingLocationId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deathDateTime;
}
