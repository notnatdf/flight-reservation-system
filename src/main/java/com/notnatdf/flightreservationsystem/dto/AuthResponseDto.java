package com.notnatdf.flightreservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String type = "Bearer";
    private String username;
    private String role;
}
