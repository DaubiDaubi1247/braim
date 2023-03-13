package ru.alex.braim.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.alex.braim.utils.interfaces.Identifiable;

import java.time.OffsetDateTime;

@Entity
@Table(name = "animal_location")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnimalLocation implements Identifiable {

    public AnimalLocation(LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationInfo locationInfo;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(name = "visit_date",columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private OffsetDateTime visitedDate;

}
