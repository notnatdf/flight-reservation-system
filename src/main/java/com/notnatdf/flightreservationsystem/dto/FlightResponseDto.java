package com.notnatdf.flightreservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDto {
    private Long id;
    private String flightNumber;
    private String departureAirport_IataCode;
    private String arrivalAirport_IataCode;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double price;
    private String aircraftModel;
    private int availableSeats;
    private int totalSeats;
}
