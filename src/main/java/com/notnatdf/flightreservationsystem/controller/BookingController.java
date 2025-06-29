package com.notnatdf.flightreservationsystem.controller;

import com.notnatdf.flightreservationsystem.dto.BookingRequestDto;
import com.notnatdf.flightreservationsystem.dto.BookingResponseDto;
import com.notnatdf.flightreservationsystem.security.UserDetailsImpl;
import com.notnatdf.flightreservationsystem.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@Valid @RequestBody BookingRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();

        BookingResponseDto createdBooking = bookingService.createBooking(requestDto, currentUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id) {
        BookingResponseDto booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<List<BookingResponseDto>> getBookingByUserId(@PathVariable Long userId) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{bookingId}/cancel")
    @PreAuthorize("hasRole('ADMIN') or @bookingAuthorizationChecker.checkBookingOwnership(#bookingId, authentication.principal.id)")
    public ResponseEntity<BookingResponseDto> cancelBooking(@PathVariable Long bookingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();

        BookingResponseDto canceledBooking = bookingService.cancelBooking(bookingId, currentUserId);
        return ResponseEntity.ok(canceledBooking);
    }
}
