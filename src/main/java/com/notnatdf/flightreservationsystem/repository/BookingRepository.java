package com.notnatdf.flightreservationsystem.repository;

import com.notnatdf.flightreservationsystem.domain.Booking;
import com.notnatdf.flightreservationsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByFlight_Id(Long flightId);

    List<Booking> findByUser_Id(Long userId);
}
