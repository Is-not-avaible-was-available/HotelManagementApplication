CREATE TABLE booking
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime              NULL,
    last_modified_at datetime              NULL,
    reference_number VARCHAR(255)          NULL,
    user_id          BIGINT                NULL,
    booking_status   Tinyint              NULL,
    hotel_id         BIGINT                NULL,
    room_id          BIGINT                NULL,
    check_in_date    date                  NULL,
    check_out_date   date                  NULL,
    price            INT                   NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE TABLE hotel
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime              NULL,
    last_modified_at datetime              NULL,
    name             VARCHAR(255)          NULL,
    address          VARCHAR(255)          NULL,
    rating           DOUBLE                NOT NULL,
    city             VARCHAR(255)          NULL,
    CONSTRAINT pk_hotel PRIMARY KEY (id)
);

CREATE TABLE hotel_rooms
(
    hotel_id BIGINT NOT NULL,
    rooms_id BIGINT NOT NULL
);

CREATE TABLE room
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime              NULL,
    last_modified_at datetime              NULL,
    room_number      INT                   NOT NULL,
    room_status      Tinyint              NULL,
    room_type_id     BIGINT                NULL,
    hotel_id         BIGINT                NULL,
    CONSTRAINT pk_room PRIMARY KEY (id)
);

CREATE TABLE room_bookings
(
    room_id     BIGINT NOT NULL,
    bookings_id BIGINT NOT NULL
);

CREATE TABLE room_type
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime              NULL,
    last_modified_at datetime              NULL,
    room_type        VARCHAR(255)          NULL,
    price            INT                   NULL,
    capacity         INT                   NULL,
    CONSTRAINT pk_roomtype PRIMARY KEY (id)
);

CREATE TABLE session
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime              NULL,
    last_modified_at datetime              NULL,
    token            VARCHAR(500)          NULL,
    user_id          BIGINT                NULL,
    session_status   TINYINT              NULL,
    expired_at       datetime              NULL,
    CONSTRAINT pk_session PRIMARY KEY (id)
);

CREATE TABLE users
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime              NULL,
    last_modified_at datetime              NULL,
    name             VARCHAR(255)          NULL,
    email            VARCHAR(255)          NULL,
    password         VARCHAR(255)          NULL,
    mobile           VARCHAR(255)          NULL,
    user_type        Tinyint              NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE hotel_rooms
    ADD CONSTRAINT uc_hotel_rooms_rooms UNIQUE (rooms_id);

ALTER TABLE room_bookings
    ADD CONSTRAINT uc_room_bookings_bookings UNIQUE (bookings_id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_HOTEL FOREIGN KEY (hotel_id) REFERENCES hotel (id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_ROOM FOREIGN KEY (room_id) REFERENCES room (id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE room
    ADD CONSTRAINT FK_ROOM_ON_HOTEL FOREIGN KEY (hotel_id) REFERENCES hotel (id);

ALTER TABLE room
    ADD CONSTRAINT FK_ROOM_ON_ROOMTYPE FOREIGN KEY (room_type_id) REFERENCES room_type (id);

ALTER TABLE session
    ADD CONSTRAINT FK_SESSION_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE hotel_rooms
    ADD CONSTRAINT fk_hotroo_on_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id);

ALTER TABLE hotel_rooms
    ADD CONSTRAINT fk_hotroo_on_room FOREIGN KEY (rooms_id) REFERENCES room (id);

ALTER TABLE room_bookings
    ADD CONSTRAINT fk_rooboo_on_booking FOREIGN KEY (bookings_id) REFERENCES booking (id);

ALTER TABLE room_bookings
    ADD CONSTRAINT fk_rooboo_on_room FOREIGN KEY (room_id) REFERENCES room (id);