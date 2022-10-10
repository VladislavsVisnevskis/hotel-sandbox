package com.wandoo.hotel.mapper;

import com.wandoo.hotel.domain.Room;
import com.wandoo.hotel.model.RoomDto;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomMapper {

    public static RoomDto mapToRoomDto(Room room) {
        if (room == null) {
            return null;
        }
        return RoomDto.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .roomType(room.getRoomType())
                .currentPrice(room.getCurrentPrice())
                .description(room.getDescription())
                .reservationList(Optional.ofNullable(room.getReservations())
                        .orElse(Collections.emptySet())
                        .stream()
                        .filter(Objects::nonNull)
                        .map(ReservationMapper::mapToReservationDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Room mapFromRoomDto(RoomDto roomDto) {
        if (roomDto == null) {
            return null;
        }
        return Room.builder()
                .id(roomDto.getId())
                .roomName(roomDto.getRoomName())
                .roomType(roomDto.getRoomType())
                .currentPrice(roomDto.getCurrentPrice())
                .description(roomDto.getDescription())
                .build();
    }
}
