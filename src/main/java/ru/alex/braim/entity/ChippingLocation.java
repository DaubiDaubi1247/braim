package ru.alex.braim.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "chip_location")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChippingLocation {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "chippingLocationList")
    List<ChippingInfo> animalList;
}
