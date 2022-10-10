package com.wandoo.hotel.service;

import com.wandoo.hotel.domain.Room;
import com.wandoo.hotel.exception.NotFoundException;
import com.wandoo.hotel.mapper.RoomMapper;
import com.wandoo.hotel.model.RoomDto;
import com.wandoo.hotel.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private static Logger logger = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::mapToRoomDto)
                .collect(Collectors.toList());
    }

    public RoomDto getRoomById(Long id) {
        logger.info("Retrieving room by id: {}", id);
        return roomRepository.findById(id)
                .map(RoomMapper::mapToRoomDto)
                .orElseThrow(() -> new NotFoundException("Room is not found by id: " + id));
    }

    public List<Room> getRoomByIds(List<Long> ids) {
        return roomRepository.findAllByIdIn(ids);
    }

    public RoomDto saveRoom(RoomDto room) {
        logger.info("Save room: {}", room);
        return RoomMapper.mapToRoomDto(roomRepository.save(RoomMapper.mapFromRoomDto(room)));
    }

    public void editRoom(Long roomId, RoomDto room) {
        if (roomRepository.findById(roomId).isPresent()) {
            saveRoom(room);
        } else {
            throw new NotFoundException("Room is not found by id: " + roomId);
        }
    }

    public void deleteRoom(Long id) {
        logger.info("Removing room with id: {}", id);
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room is not found by id: " + id));
        roomRepository.delete(room);
        logger.info("Room with id: {} is deleted", id);
    }
}
