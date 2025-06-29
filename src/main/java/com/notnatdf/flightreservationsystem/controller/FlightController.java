package com.notnatdf.flightreservationsystem.controller;

import com.notnatdf.flightreservationsystem.dto.FlightRequestDto;
import com.notnatdf.flightreservationsystem.dto.FlightResponseDto;
import com.notnatdf.flightreservationsystem.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightResponseDto> createFlight(@Valid @RequestBody FlightRequestDto requestDto) {
        FlightResponseDto createdFlight = flightService.createFlight(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }

    @GetMapping
    public ResponseEntity<List<FlightResponseDto>> getAllFlights() {
        List<FlightResponseDto> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDto> getFlightById(@PathVariable Long id) {
        FlightResponseDto flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightResponseDto> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightRequestDto requestDto) {
        FlightResponseDto updatedFlight = flightService.updateFlight(id, requestDto);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightResponseDto>> searchFlights(
            @RequestParam String departure, @RequestParam String arrival,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime date) {
        List<FlightResponseDto> flights = flightService.searchFlights(departure, arrival, date);
        return ResponseEntity.ok(flights);
    }

    @PostMapping("/{flightId}/seats/generate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> generateSeats(@PathVariable Long flightId) {
        flightService.generateSeatsForFlight(flightId);
        return ResponseEntity.ok().build();
    }
}
