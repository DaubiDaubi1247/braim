package ru.alex.braim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alex.braim.utils.enums.LifeStatusEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animal")
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @OneToOne(mappedBy = "animal")
    ChippingInfo chippingInfo;

    @ManyToMany
    @JoinTable(
            name = "animal_type",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @Builder.Default
    private List<AnimalType> animalTypeList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "animal_location",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    @Builder.Default
    private List<LocationInfo> locationList = new ArrayList<>();


}
