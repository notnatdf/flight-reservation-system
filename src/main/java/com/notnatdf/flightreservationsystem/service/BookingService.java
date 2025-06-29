package com.notnatdf.flightreservationsystem.service;

import com.notnatdf.flightreservationsystem.domain.*;
import com.notnatdf.flightreservationsystem.dto.BookingRequestDto;
import com.notnatdf.flightreservationsystem.dto.BookingResponseDto;
import com.notnatdf.flightreservationsystem.repository.BookingRepository;
import com.notnatdf.flightreservationsystem.repository.FlightRepository;
import com.notnatdf.flightreservationsystem.repository.SeatRepository;
import com.notnatdf.flightreservationsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto requestDto, Long userId) {
        Flight flight = flightRepository.findById(requestDto.getFlightId())
                .orElseThrow(() -> new IllegalArgumentException("Flight not found with ID: " + requestDto.getFlightId()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book a flight that has already departed");
        }

        List<String> selectedSeatNumbers = requestDto.getSelectedSeats();

        List<Seat> selectedSeats = selectedSeatNumbers.stream()
                        .map(seatNumber -> {
                            Seat seat = seatRepository.findByFlightAndSeatNumber(flight, seatNumber)
                                    .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + seatNumber));

                            if (!seat.isAvailable()) {
                                throw new IllegalStateException("Seat " + seatNumber + " is already booked");
                            }
                            return seat;
                        })
                        .collect(Collectors.toList());

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setUser(user);
        booking.setPassengerName(requestDto.getPassengerName());
        booking.setPassengerEmail(requestDto.getPassengerEmail());
        booking.setNumberOfPassengers(selectedSeats.size());
        booking.setTotalPrice(requestDto.getTotalPrice());
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepository.save(booking);

        selectedSeats.forEach(seat -> {
            seat.setAvailable(false);
            seat.setBooking(savedBooking);
            seatRepository.save(seat);
        });

        return convertToDto(savedBooking);
    }

    public BookingResponseDto getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + id));
    }

    public List<BookingResponseDto> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUser_Id(userId);
        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public BookingResponseDto cancelBooking(Long bookingId, Long requestingUserId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELED) {
            throw new IllegalArgumentException("Booking is already canceled.");
        }
        if (booking.getFlight().getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot cancel a booing for a flight that has already departed.");
        }

        Flight flight = booking.getFlight();
        flight.increaseAvailableSeats(booking.getNumberOfPassengers());
        flightRepository.save(flight);

        booking.setStatus(BookingStatus.CANCELED);
        Booking canceledBooking = bookingRepository.save(booking);
        return convertToDto(canceledBooking);
    }

    private BookingResponseDto convertToDto(Booking booking) {
        return new BookingResponseDto(
                booking.getId(),
                booking.getFlightId(),
                booking.getUserId(),
                booking.getPassengerName(),
                booking.getPassengerEmail(),
                booking.getNumberOfPassengers(),
                booking.getStatus().name(),
                booking.getBookingDate(),
                booking.getLastModifiedDate(),
                booking.getTotalPrice()
        );
    }
}
