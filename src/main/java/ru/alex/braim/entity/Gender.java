package ru.alex.braim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "gender")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gender", unique = true)
    @NotNull
    private String gender;

    @OneToMany(mappedBy = "gender")
    private List<Animal> animals;
}
