package ru.alex.braim.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class AnimalDtoSpecification extends AnimalDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDateTime;

    @Min(1)
    private Integer chipperId;

    @Min(1)
    private Long chippingLocationId;

    @Min(0)
    private Integer from;

    @Min(1)
    private Integer size;

}
