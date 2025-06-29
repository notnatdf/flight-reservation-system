INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE-ADMIN');

INSERT INTO users (id, username, password, email) VALUES (1, 'adminuser', '$2a$10$jrFdRuDOi35VKadqXRNVn.yBpivKsQrUUPxPYUf5Jdn1dQrF1.JqO', 'admin@example.com') ON DUPLICATE KEY UPDATE username=username;
INSERT INTO users (id, username, password, email) VALUES (2, 'newuser', '$2a$10$sk3L9Mt9eAG54OMsdgJ33uik8V2a22y9PkXe/JOFPP5EzwoE03/0u', 'newuser@example.com') ON DUPLICATE KEY UPDATE username=username;

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (2, 1);

INSERT IGNORE INTO airports (id, iata_code, name, city) VALUES (1, 'ICN', 'Incheon International Airport', 'Seoul');
INSERT IGNORE INTO airports (id, iata_code, name, city) VALUES (2, 'LAX', 'Los Angeles International Airport', 'Los Angeles');
INSERT IGNORE INTO airports (id, iata_code, name, city) VALUES (3, 'JFK', 'John F. Kennedy International Airport', 'New York');
INSERT IGNORE INTO airports (id, iata_code, name, city) VALUES (4, 'NRT', 'Narita International Airport', 'Tokyo');

INSERT IGNORE INTO aircraft (id, model, total_seats) VALUES (1, 'Boeing 747', 400);
INSERT IGNORE INTO aircraft (id, model, total_seats) VALUES (2, 'Airbus A320', 180);
INSERT IGNORE INTO aircraft (id, model, total_seats) VALUES (3, 'Boeing 737', 150);
