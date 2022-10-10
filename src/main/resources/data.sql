create TABLE users
(
    id               BIGINT         NOT NULL    AUTO_INCREMENT,
    first_name       VARCHAR(50)    NOT NULL,
    last_name        VARCHAR(50)    NOT NULL,
    user_type        VARCHAR(10)    DEFAULT 'GUEST',
    email            VARCHAR(50)    NOT NULL,
    phone            VARCHAR(20)    NOT NULL,
    address          VARCHAR(255)   NOT NULL,
    PRIMARY KEY (id)
);

create TABLE rooms
(
    id               BIGINT         NOT NULL    AUTO_INCREMENT,
    room_name        VARCHAR(50)    NOT NULL,
    room_type        VARCHAR(50)    NOT NULL,
    current_price    NUMERIC(20, 2) NOT NULL,
    description      VARCHAR(1000)  NOT NULL,
    PRIMARY KEY (id)
);

create TABLE reservations
(
    id                      BIGINT          NOT NULL    AUTO_INCREMENT,
    user_id                 BIGINT          NOT NULL,
    start_date              DATE            NOT NULL,
    end_date                DATE            NOT NULL,
    reservation_status      VARCHAR(20)     NOT NULL,
    discount                NUMERIC(20, 2)  DEFAULT  NULL,
    total_amount            NUMERIC(20, 2)  NOT NULL,
    details                 VARCHAR(1000)   DEFAULT  NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

create TABLE room_reservation
(
    id                  BIGINT          NOT NULL    AUTO_INCREMENT,
    room_id             BIGINT,
    reservation_id      BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES rooms (id),
    FOREIGN KEY (reservation_id) REFERENCES reservations (id)
);

insert into rooms (room_name, room_type, current_price, description) values ('101', 'STANDARD', 69.75, 'Standart room');
insert into rooms (room_name, room_type, current_price, description) values ('202', 'SUITE', 169.75, 'Suite room');
insert into rooms (room_name, room_type, current_price, description) values ('303', 'FAMILY', 269.75, 'Family room');
insert into rooms (room_name, room_type, current_price, description) values ('404', 'APARTMENT', 369.75, 'Apartment');
insert into rooms (room_name, room_type, current_price, description) values ('505', 'LUX', 469.75, 'Lux room');

insert into users (first_name, last_name, user_type, email, phone, address) values ('Tony', 'Stark', 'ADMIN', 'tony.stark@wandoo.com', '+1', 'NY');
insert into users (first_name, last_name, user_type, email, phone, address) values ('Bruce', 'Banner', 'GUEST', 'bruce.banner@wandoo.com', '+1 23', 'LA');
insert into users (first_name, last_name, user_type, email, phone, address) values ('Peter', 'Parker', 'GUEST', 'peter.parker@wandoo.com', '+1 69', 'Queens');

insert into reservations (user_id, start_date, end_date, reservation_status, total_amount) values ( 2, '2022-10-01', '2022-10-03', 'APPROVED', 1469.75);
insert into reservations (user_id, start_date, end_date, reservation_status, total_amount) values ( 3, '2022-10-02', '2022-10-05', 'APPROVED', 725.75);

insert into room_reservation (room_id, reservation_id) values ( 1, 1);
insert into room_reservation (room_id, reservation_id) values ( 2, 1);
insert into room_reservation (room_id, reservation_id) values ( 3, 2);
