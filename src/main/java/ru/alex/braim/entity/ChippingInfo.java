package ru.alex.braim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

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
    @CreationTimestamp
    private Date chippingDateTime;

    @ManyToOne
    @JoinColumn(name = "chipper_id")
    private Account chipper;

    @ManyToMany
    @JoinTable(
            name = "chiping_location",
            joinColumns = "chiping_id",
            inverseJoinColumns = "location_id"
    )
    private List<ChippingLocation> chippingLocationList;

    @Column(name = "death_time")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date deathDateTime;

}
