package com.notnatdf.flightreservationsystem.dto;

import com.notnatdf.flightreservationsystem.domain.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private Long id;
    private Long flightId;
    private Long userId;
    private String passengerName;
    private String passengerEmail;
    private int numberOfPassengers;
    private String status;
    private LocalDateTime bookingDate;
    private LocalDateTime lastModifiedDate;
    private Double totalPrice;
}
