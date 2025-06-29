package com.notnatdf.flightreservationsystem.repository;

import com.notnatdf.flightreservationsystem.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByIataCode(String iataCode);
    Optional<Airport> findByName(String name);
}
