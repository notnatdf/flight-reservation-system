package com.notnatdf.flightreservationsystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, unique = true, length = 3)
    private String iataCode;

    @OneToMany(mappedBy = "departureAirport")
    @ToString.Exclude
    private List<Flight> departingFlights;

    @OneToMany(mappedBy = "arrivalAirport")
    @ToString.Exclude
    private List<Flight> arrivalFlights;
}
