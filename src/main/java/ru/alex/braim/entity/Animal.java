package ru.alex.braim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.alex.braim.utils.enums.LifeStatusEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Animal {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    @NotNull
    private Float weight;

    @Column(name = "length")
    @NotNull
    private Float length;

    @Column(name = "height")
    @NotNull
    private Float height;

    @Column(name = "gender")
    @NotNull
    private String gender;

    @Column(name = "life_status")
    @NotNull
    @Builder.Default
    private String lifeStatus = LifeStatusEnum.ALIVE.getLifeStatus();

    @OneToOne(mappedBy = "animal", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    ChippingInfo chippingInfo;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "animal_type",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @Builder.Default
    private List<AnimalType> animalTypeList = new ArrayList<>();

    @OneToMany(mappedBy = "animal", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<AnimalLocation> animalLocations = new ArrayList<>();

    public void addLocationToAnimal(AnimalLocation animalLocation) {
        animalLocation.setAnimal(this);

        animalLocations.add(animalLocation);
    }

    public List<LocationInfo> animalLocationToLocationInfo() {
        return animalLocations.stream().
                map(AnimalLocation::getLocationInfo).
                toList();

    }

}
