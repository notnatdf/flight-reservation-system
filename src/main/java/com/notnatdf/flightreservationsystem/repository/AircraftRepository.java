package com.notnatdf.flightreservationsystem.repository;

import com.notnatdf.flightreservationsystem.domain.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    Optional<Aircraft> findByModel(String model);
}
