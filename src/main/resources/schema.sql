CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS airports (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    iata_code VARCHAR(3) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS aircraft (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR(255) NOT NULL UNIQUE,
    total_seats INT NOT NULL
);

CREATE TABLE IF NOT EXISTS flights (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    flight_number VARCHAR(255) NOT NULL UNIQUE,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    price DOUBLE NOT NULL,
    available_seats INT NOT NULL,
    total_seats INT NOT NULL,
    departure_airport_id BIGINT NOT NULL,
    arrival_airport_id BIGINT NOT NULL,
    aircraft_id BIGINT NOT NULL,

    FOREIGN KEY (departure_airport_id) REFERENCES airports(id),
    FOREIGN KEY (arrival_airport_id) REFERENCES airports(id),
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(id)
);

CREATE TABLE IF NOT EXISTS seats (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    seat_number VARCHAR(10) NOT NULL,
    is_booked BOOLEAN NOT NULL,
    flight_id BIGINT NOT NULL,

    FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE
);