package com.notnatdf.flightreservationsystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String flightNumber;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    @ToString.Exclude
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    @ToString.Exclude
    private Airport arrivalAirport;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    @ToString.Exclude
    private Aircraft aircraft;

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false)
    private int totalSeats;

    public void increaseAvailableSeats(int count) {
        if (this.availableSeats + count <= this.totalSeats) {
            this.availableSeats += count;
        } else {
            throw new IllegalArgumentException("Cannot exceed total seats capacity.");
        }
    }

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seat> seats;
}
