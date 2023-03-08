package ru.alex.braim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.alex.braim.utils.interfaces.Identifiable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class LocationInfo implements Identifiable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    @NotNull
    private Double latitude;

    @Column(name = "longitude")
    @NotNull
    private Double longitude;

    @OneToMany(mappedBy = "locationInfo", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<AnimalLocation> animalLocationList = new ArrayList<>();

    @OneToOne(mappedBy = "locationInfo")
    private ChippingInfo chippingInfo;

}
