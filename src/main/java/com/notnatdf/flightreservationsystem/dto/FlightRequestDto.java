package com.notnatdf.flightreservationsystem.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequestDto {
    @NotBlank(message = "항공편 번호는 필수입니다.")
    private String flightNumber;

    @NotBlank(message = "출발 공항 코드는 필수입니다.")
    private String departureAirportIataCode;

    @NotBlank(message = "도착 공항 코드는 필수입니다.")
    private String arrivalAirportIataCode;

    @NotNull(message = "출발 시간은 필수입니다.")
    @FutureOrPresent(message = "출발 시간은 현재 또는 미래여야 합니다.")
    private LocalDateTime departureTime;

    @NotNull(message = "도착 시간은 필수입니다.")
    @FutureOrPresent(message = "도착 시간은 현재 또는 미래여야 합니다.")
    private LocalDateTime arrivalTime;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Double price;

    @NotBlank(message = "항공기 모델은 필수입니다.")
    private String aircraftModel;

    @Min(value = 0, message = "총 좌석 수는 0 이상이어야 합니다.")
    private int totalSeats;
}
