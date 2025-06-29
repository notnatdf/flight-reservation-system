package com.notnatdf.flightreservationsystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "aircraft")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String model;

    @Column(nullable = false)
    private int totalSeats;

    @OneToMany(mappedBy = "aircraft")
    @ToString.Exclude
    private List<Flight> flights;
}
