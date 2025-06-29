package com.notnatdf.flightreservationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FlightReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightReservationSystemApplication.class, args);
    }

}
