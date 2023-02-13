package ru.alex.braim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alex.braim.utils.enums.LifeStatusEnum;

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

}
