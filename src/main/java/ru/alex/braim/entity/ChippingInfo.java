package ru.alex.braim.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.alex.braim.utils.interfaces.Identifiable;

import java.sql.Timestamp;

@Entity
@Table(name = "chip_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChippingInfo implements Identifiable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chip_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp chippingDateTime;

    @ManyToOne
    @JoinColumn(name = "chipper_id")
    private Account chipper;

    @OneToOne
    @JoinColumn(name = "animal_id")
    @ToString.Exclude
    private Animal animal;

    @Column(name = "death_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp deathDateTime = null;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationInfo locationInfo;

}
