package com.notnatdf.flightreservationsystem.repository;

import com.notnatdf.flightreservationsystem.domain.Flight;
import com.notnatdf.flightreservationsystem.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByFlightAndSeatNumber(Flight flight, String seatNumber);
}
