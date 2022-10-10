package com.wandoo.hotel.mapper;

import com.wandoo.hotel.domain.Reservation;
import com.wandoo.hotel.domain.Room;
import com.wandoo.hotel.model.ReservationDto;

import java.util.stream.Collectors;

public class ReservationMapper {

    public static ReservationDto mapToReservationDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        return ReservationDto.builder()
                .id(reservation.getId())
                .guestId(reservation.getGuest().getId())
                .roomIdList(reservation.getRooms().stream()
                        .map(Room::getId)
                        .collect(Collectors.toList()))
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .status(reservation.getStatus())
                .discount(reservation.getDiscount())
                .totalAmount(reservation.getTotalAmount())
                .details(reservation.getDetails())
                .build();
    }

    public static Reservation mapFromReservationDto(ReservationDto reservationDto) {
        if (reservationDto == null) {
            return null;
        }
        return Reservation.builder()
                .id(reservationDto.getId())
                .startDate(reservationDto.getStartDate())
                .endDate(reservationDto.getEndDate())
                .status(reservationDto.getStatus())
                .discount(reservationDto.getDiscount())
                .totalAmount(reservationDto.getTotalAmount())
                .details(reservationDto.getDetails())
                .build();
    }
}
