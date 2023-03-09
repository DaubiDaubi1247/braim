package ru.alex.braim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alex.braim.utils.interfaces.Identifiable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AnimalType implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String type;

    @ManyToMany
    @JoinTable(
            name = "animal_type",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id")
    )
    private List<Animal> animalList = new ArrayList<>();
}
