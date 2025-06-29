package com.notnatdf.flightreservationsystem.security;

import com.notnatdf.flightreservationsystem.domain.Booking;
import com.notnatdf.flightreservationsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookingAuthorizationChecker {

    private final BookingRepository bookingRepository;

    public boolean checkBookingOwnership(Long bookingId, Long userId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            return booking.getUserId().equals(userId);
        }

        return false;
    }
}
