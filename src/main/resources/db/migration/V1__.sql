CREATE TABLE booking
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime              NULL,
    last_modified_at datetime              NULL,
    reference_number VARCHAR(255)          NULL,
    user_id          BIGINT                NULL,
    booking_status   tinyint              NULL,
    hotel_id         BIGINT                NULL,
    room_id          BIGINT                NULL,
    check_in_date    date                  NULL,
    check_out_date   date                  NULL,
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
    CONSTRAINT pk_hotel PRIMARY KEY (id)
);

CREATE TABLE room
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime              NULL,
    last_modified_at datetime              NULL,
    room_number      INT                   NOT NULL,
    capacity         INT                   NOT NULL,
    room_status      Tinyint             NULL,
    room_type        TINYINT              NULL,
    hotel_id         BIGINT                NULL,
    CONSTRAINT pk_room PRIMARY KEY (id)
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
    user_type        TINYINT              NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

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

ALTER TABLE session
    ADD CONSTRAINT FK_SESSION_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);