package ru.alex.braim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "chip_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChippingInfo {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chip_date")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date chippingDateTime;

    @ManyToOne
    @JoinColumn(name = "chipper_id")
    private Account chipper;
}
