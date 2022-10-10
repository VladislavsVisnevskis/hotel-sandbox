package com.wandoo.hotel.mapper;

import com.wandoo.hotel.domain.User;
import com.wandoo.hotel.model.UserDto;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .type(user.getType())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .email(user.getEmail())
                .reservationList(user.getReservationList().stream()
                        .map(ReservationMapper::mapToReservationDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static User mapFromUserDto(UserDto user) {
        if (user == null) {
            return null;
        }
        return User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .type(user.getType())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .email(user.getEmail())
                .build();
    }
}
