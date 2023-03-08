package ru.alex.braim.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "animal_location")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnimalLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationInfo locationInfo;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
