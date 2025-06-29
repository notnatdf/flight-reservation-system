package com.notnatdf.flightreservationsystem.repository;

import com.notnatdf.flightreservationsystem.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findByDepartureAirport_IataCodeAndArrivalAirport_IataCodeAndDepartureTimeBetween(
            String departureIata, String arrivalIata, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT f FROM Flight f WHERE DATE(f.departureTime) = DATE(:date)")
    List<Flight> findByDepartureTime(@Param("date") LocalDateTime date);
}
