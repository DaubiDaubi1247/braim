package ru.alex.braim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alex.braim.utils.enums.LifeStatusEnum;

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

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Column(name = "life_status")
    @NotNull
    @Builder.Default
    private String lifeStatus = LifeStatusEnum.ALIVE.getLifeStatus();

    @ManyToMany
    @JoinTable(
            name = "animal_chipping",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "chiping_id")
    )
    List<ChippingInfo> chippingInfoList;

    @ManyToMany
    @JoinTable(
            name = "animal_type",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn("type_id")
    )
    private List<AnimalTypes> animalTypesList;

}
