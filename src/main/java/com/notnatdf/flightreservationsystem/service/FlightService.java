package com.notnatdf.flightreservationsystem.service;

import com.notnatdf.flightreservationsystem.domain.Aircraft;
import com.notnatdf.flightreservationsystem.domain.Airport;
import com.notnatdf.flightreservationsystem.domain.Flight;
import com.notnatdf.flightreservationsystem.domain.Seat;
import com.notnatdf.flightreservationsystem.dto.FlightRequestDto;
import com.notnatdf.flightreservationsystem.dto.FlightResponseDto;
import com.notnatdf.flightreservationsystem.repository.AircraftRepository;
import com.notnatdf.flightreservationsystem.repository.AirportRepository;
import com.notnatdf.flightreservationsystem.repository.FlightRepository;
import com.notnatdf.flightreservationsystem.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final AircraftRepository aircraftRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public FlightResponseDto createFlight(FlightRequestDto requestDto) {
        Airport departureAirport = airportRepository.findByIataCode(requestDto.getDepartureAirportIataCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid departure airport IATA code: " + requestDto.getDepartureAirportIataCode()));
        Airport arrivalAirport = airportRepository.findByIataCode(requestDto.getArrivalAirportIataCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid arrival airport IATA code: " + requestDto.getArrivalAirportIataCode()));
        Aircraft aircraft = aircraftRepository.findByModel(requestDto.getAircraftModel())
                .orElseThrow(() -> new IllegalArgumentException("Invalid aircraft model: " + requestDto.getAircraftModel()));

        if (requestDto.getDepartureTime().isAfter(requestDto.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time cannot be after arrival time");
        }

        Flight flight = new Flight();
        flight.setFlightNumber(requestDto.getFlightNumber());
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(requestDto.getDepartureTime());
        flight.setArrivalTime(requestDto.getArrivalTime());
        flight.setPrice(requestDto.getPrice());
        flight.setAircraft(aircraft);
        flight.setTotalSeats(requestDto.getTotalSeats());
        flight.setAvailableSeats(requestDto.getTotalSeats());

        Flight savedFlight = flightRepository.save(flight);
        return convertToDto(savedFlight);
    }

    public List<FlightResponseDto> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public FlightResponseDto getFlightById(Long id) {
        return flightRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found with ID: " + id));
    }

    @Transactional
    public FlightResponseDto updateFlight(Long id, FlightRequestDto requestDto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found with ID: " + id));

        flight.setFlightNumber(requestDto.getFlightNumber());
        flight.setDepartureAirport(airportRepository.findByIataCode(requestDto.getDepartureAirportIataCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid departure airport IATA code: " + requestDto.getDepartureAirportIataCode())));
        flight.setArrivalAirport(airportRepository.findByIataCode(requestDto.getArrivalAirportIataCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid arrival airport IATA code: " + requestDto.getArrivalAirportIataCode())));
        flight.setDepartureTime(requestDto.getDepartureTime());
        flight.setArrivalTime(requestDto.getArrivalTime());
        flight.setPrice(requestDto.getPrice());
        flight.setAircraft(aircraftRepository.findByModel(requestDto.getAircraftModel())
                .orElseThrow(() -> new IllegalArgumentException("Invalid aircraft model: " + requestDto.getAircraftModel())));
        flight.setTotalSeats(requestDto.getTotalSeats());

        Flight updatedFlight = flightRepository.save(flight);
        return convertToDto(updatedFlight);
    }

    @Transactional
    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new IllegalArgumentException("Flight not found with ID: " + id);
        }
        flightRepository.deleteById(id);
    }

    public List<FlightResponseDto> searchFlights(String departureIata, String arrivalIata, LocalDateTime date) {
        LocalDateTime startTime = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = date.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        List<Flight> flights = flightRepository.findByDepartureAirport_IataCodeAndArrivalAirport_IataCodeAndDepartureTimeBetween(
                departureIata, arrivalIata, startTime, endTime);

        return flights.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FlightResponseDto convertToDto(Flight flight) {
        return new FlightResponseDto(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getDepartureAirport() != null ? flight.getDepartureAirport().getIataCode() : null,
                flight.getArrivalAirport() != null ? flight.getArrivalAirport().getIataCode() : null,
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getPrice(),
                flight.getAircraft() != null ? flight.getAircraft().getModel() : null,
                flight.getAvailableSeats(),
                flight.getTotalSeats()
        );
    }

    @Transactional
    public void generateSeatsForFlight(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found with ID: " + flightId));

        for (char row = 'A'; row <= 'C'; row++) {
            for (int num = 1; num <= 10; num++) {
                Seat seat = new Seat();
                seat.setSeatNumber(String.valueOf(row + num));
                seat.setFlight(flight);
                seat.setAvailable(true);
                seatRepository.save(seat);
            }
        }
    }
}
