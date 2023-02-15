package ru.alex.braim.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChippingInfoDto {
    @Temporal(TemporalType.TIMESTAMP)
    private Date chippingDateTime;

    private Integer chipperId;

    private Integer chippingLocationId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deathDateTime;
}
