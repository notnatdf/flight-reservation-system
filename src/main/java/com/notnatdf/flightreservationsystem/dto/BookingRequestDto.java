package com.notnatdf.flightreservationsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {

    @NotNull(message = "항공편 ID는 필수입니다.")
    private Long flightId;

    @NotNull(message = "승객 이름은 필수입니다.")
    private String passengerName;

    @NotNull(message = "승객 이메일은 필수입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String passengerEmail;

    @NotNull(message = "총 가격은 필수입니다.")
    @Min(value = 0, message = "총 가격은 0 이상이어야 합니다.")
    private Double totalPrice;

    @NotNull(message = "선택된 좌석은 필수입니다.")
    private List<String> selectedSeats;

}
