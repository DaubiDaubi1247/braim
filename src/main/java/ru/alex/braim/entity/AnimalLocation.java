package ru.alex.braim.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.alex.braim.utils.interfaces.Identifiable;

import java.util.Date;

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

    @Column(name = "visit_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date visitedDate;

}
