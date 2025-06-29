package com.notnatdf.flightreservationsystem.service;

import com.notnatdf.flightreservationsystem.domain.Role;
import com.notnatdf.flightreservationsystem.domain.User;
import com.notnatdf.flightreservationsystem.dto.AuthResponseDto;
import com.notnatdf.flightreservationsystem.dto.LoginRequestDto;
import com.notnatdf.flightreservationsystem.dto.SignupRequestDto;
import com.notnatdf.flightreservationsystem.repository.UserRepository;
import com.notnatdf.flightreservationsystem.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public String registerUser(SignupRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(requestDto.getRole() != null ? requestDto.getRole() : Role.USER);

        userRepository.save(user);
        return "User registered successfully!";
    }

    public AuthResponseDto authenticateUser(LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateToken(authentication.getName());

        return new AuthResponseDto(jwt, "Bearer", authentication.getName(), authentication.getAuthorities().iterator().next().getAuthority());
    }
}
