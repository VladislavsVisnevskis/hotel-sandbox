package com.wandoo.hotel.service;

import com.wandoo.hotel.domain.Room;
import com.wandoo.hotel.domain.Room;
import com.wandoo.hotel.exception.NotFoundException;
import com.wandoo.hotel.mapper.RoomMapper;
import com.wandoo.hotel.mapper.RoomMapper;
import com.wandoo.hotel.model.RoomDto;
import com.wandoo.hotel.model.RoomDto;
import com.wandoo.hotel.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    protected RoomRepository roomRepository;

    @InjectMocks
    protected RoomService service;

    @Test
    void getAllRoomsTest() {
        List<Room> rooms = getRoomList(List.of(1L, 2L, 3L));
        when(roomRepository.findAll()).thenReturn(rooms);
        List<RoomDto> expected = getRoomDtoList(List.of(1L, 2L, 3L));
        try (MockedStatic<RoomMapper> utilities = Mockito.mockStatic(RoomMapper.class)) {
            utilities.when(() -> RoomMapper.mapToRoomDto(rooms.get(0))).thenReturn(expected.get(0));
            utilities.when(() -> RoomMapper.mapToRoomDto(rooms.get(1))).thenReturn(expected.get(1));
            utilities.when(() -> RoomMapper.mapToRoomDto(rooms.get(2))).thenReturn(expected.get(2));

            List<RoomDto> result = service.getAllRooms();

            assertEquals(expected.size(), result.size());
            verify(roomRepository).findAll();
        }
    }

    @Test
    void getRoomDtoByIdTest() {
        Room room = getRoom(69L);
        when(roomRepository.findById(69L)).thenReturn(Optional.of(room));
        RoomDto expected = getRoomDto(69L);
        try (MockedStatic<RoomMapper> utilities = Mockito.mockStatic(RoomMapper.class)) {
            utilities.when(() -> RoomMapper.mapToRoomDto(room)).thenReturn(expected);

            RoomDto result = service.getRoomById(69L);

            assertEquals(expected, result);
            verify(roomRepository).findById(69L);
        }
    }

    @Test
    void getRoomByIdTest_whenNotPresent() {
        Room room = getRoom(69L);
        when(roomRepository.findById(69L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getRoomById(69L));
    }

    @Test
    void saveRoomTest() {
        Room room = getRoom(69L);
        RoomDto roomToSave = getRoomDto(69L);
        when(roomRepository.save(room)).thenReturn(room);
        RoomDto expected = getRoomDto(69L);

        try (MockedStatic<RoomMapper> utilities = Mockito.mockStatic(RoomMapper.class)) {
            utilities.when(() -> RoomMapper.mapToRoomDto(room)).thenReturn(expected);
            utilities.when(() -> RoomMapper.mapFromRoomDto(roomToSave)).thenReturn(room);

            RoomDto result = service.saveRoom(roomToSave);

            assertEquals(expected, result);
            verify(roomRepository).save(room);
        }
    }

    @Test
    void deleteRoomTest() {
        Room room = getRoom(69L);
        RoomDto roomToSave = getRoomDto(69L);
        when(roomRepository.findById(69L)).thenReturn(Optional.of(room));
        doNothing().when(roomRepository).delete(room);

        service.deleteRoom(69L);

        verify(roomRepository).findById(69L);
        verify(roomRepository).delete(room);
    }

    private List<Room> getRoomList(List<Long> ids) {
        return ids.stream().map(this::getRoom)
                .collect(Collectors.toList());
    }

    private Room getRoom(Long id) {
        return Room.builder()
                .id(id)
                .roomName("first room")
                .build();
    }

    private List<RoomDto> getRoomDtoList(List<Long> ids) {
        return ids.stream().map(this::getRoomDto)
                .collect(Collectors.toList());
    }

    private RoomDto getRoomDto(Long id) {
        return RoomDto.builder()
                .id(id)
                .roomName("first room")
                .build();
    }
}