package com.notnatdf.flightreservationsystem.controller;

import com.notnatdf.flightreservationsystem.dto.AuthResponseDto;
import com.notnatdf.flightreservationsystem.dto.LoginRequestDto;
import com.notnatdf.flightreservationsystem.dto.SignupRequestDto;
import com.notnatdf.flightreservationsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequestDto requestDto) {
        String message = authService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto requestDto) {
        AuthResponseDto response = authService.authenticateUser(requestDto);
        return ResponseEntity.ok(response);
    }
}
