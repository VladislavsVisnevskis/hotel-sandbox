package com.wandoo.hotel.service;

import com.wandoo.hotel.domain.Reservation;
import com.wandoo.hotel.domain.ReservationStatus;
import com.wandoo.hotel.domain.Room;
import com.wandoo.hotel.domain.User;
import com.wandoo.hotel.exception.ServiceException;
import com.wandoo.hotel.mapper.ReservationMapper;
import com.wandoo.hotel.model.ReservationDto;
import com.wandoo.hotel.model.RoomDto;
import com.wandoo.hotel.model.UserDto;
import com.wandoo.hotel.model.request.ReservationRequest;
import com.wandoo.hotel.model.response.AvailabilityResponse;
import com.wandoo.hotel.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    private static final LocalDate START_DATE = LocalDate.of(2022, 10, 1);
    private static final LocalDate END_DATE = LocalDate.of(2022, 10, 2);

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RoomService roomService;
    @Mock
    private UserService userService;

    @InjectMocks
    private ReservationService service;

    @Test
    void getAllReservationsWithinPeriodTest() {
        LocalDate startDate = LocalDate.of(2022, 10, 1);
        LocalDate endDate = LocalDate.of(2022, 10, 8);
        List<Reservation> reservationList = getReservationList(List.of(1L, 2L, 3L));
        List<ReservationDto> expected = getReservationDtoList(List.of(1L, 2L, 3L));

        when(reservationRepository.findAllWithinDateInterval(startDate, endDate)).thenReturn(reservationList);

        try (MockedStatic<ReservationMapper> utilities = Mockito.mockStatic(ReservationMapper.class)) {
            utilities.when(() -> ReservationMapper.mapToReservationDto(reservationList.get(0))).thenReturn(expected.get(0));
            utilities.when(() -> ReservationMapper.mapToReservationDto(reservationList.get(1))).thenReturn(expected.get(1));
            utilities.when(() -> ReservationMapper.mapToReservationDto(reservationList.get(2))).thenReturn(expected.get(2));

            List<ReservationDto> result = service.getAllReservationsWithinPeriod(startDate, endDate);

            assertEquals(expected.size(), result.size());
            verify(reservationRepository).findAllWithinDateInterval(startDate, endDate);
        }
    }

    @Test
    void checkAllRoomAvailabilityTest() {
        List<Reservation> reservationList = getReservationList(List.of(1L));
        List<ReservationDto> reservationDtoList = getReservationDtoList(List.of(1L));

        when(reservationRepository.findAllWithinDateInterval(START_DATE, END_DATE)).thenReturn(reservationList);
        when(roomService.getAllRooms()).thenReturn(List.of(getRoomDto(23L)));

        List<AvailabilityResponse> expected = getAvailabilityResponseList();

        try (MockedStatic<ReservationMapper> utilities = Mockito.mockStatic(ReservationMapper.class)) {
            utilities.when(() -> ReservationMapper.mapToReservationDto(reservationList.get(0))).thenReturn(reservationDtoList.get(0));

            List<AvailabilityResponse> result = service.checkAllRoomAvailability(START_DATE, END_DATE);

            assertEquals(expected.size(), result.size());
            assertEquals(expected.get(0), result.get(0));
            verify(reservationRepository).findAllWithinDateInterval(START_DATE, END_DATE);
            verify(roomService).getAllRooms();
        }
    }

    @Test
    void saveReservationTest() throws ServiceException {

        User user = getUser(69L);
        UserDto userDto = getUserDto(69L);
        Room room = getRoom(23L);
        RoomDto roomDto = getRoomDto(23L);
        ;
        ReservationRequest reservationRequest = getReservationRequest();
        ReservationDto expected = getReservationDto(111L);

        when(reservationRepository.findAllWithinDateInterval(START_DATE, END_DATE)).thenReturn(emptyList());
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(roomService.getRoomByIds(List.of(23L))).thenReturn(List.of(room));
        when(roomService.getRoomById(23L)).thenReturn(roomDto);

        try (MockedStatic<ReservationMapper> utilities = Mockito.mockStatic(ReservationMapper.class)) {
            utilities.when(() -> ReservationMapper.mapToReservationDto(any())).thenReturn(expected);

            ReservationDto result = service.saveReservation(reservationRequest);

            assertEquals(expected, result);
            verify(reservationRepository).findAllWithinDateInterval(START_DATE, END_DATE);
            verify(userService).getUserById(anyLong());
            verify(roomService).getRoomByIds(any());
        }
    }

    @Test
    void saveReservationTest_whenInvalidDate() throws ServiceException {

        ReservationRequest request = ReservationRequest.builder()
                .userId(23L)
                .roomIds(List.of(69L))
                .fromDate(END_DATE)
                .toDate(START_DATE)
                .build();

        assertThrows(ServiceException.class, () -> service.saveReservation(request));
    }

    private User getUser(Long id) {
        return User.builder()
                .id(id)
                .firstName("John")
                .build();
    }

    private UserDto getUserDto(Long id) {
        return UserDto.builder()
                .id(id)
                .firstName("John")
                .build();
    }

    private ReservationRequest getReservationRequest() {
        return ReservationRequest.builder()
                .fromDate(START_DATE)
                .toDate(END_DATE)
                .roomIds(List.of(23L))
                .description("description")
                .userId(69L)
                .build();
    }

    private List<AvailabilityResponse> getAvailabilityResponseList() {
        return List.of(getAvailabilityResponseTrue());
    }


    private AvailabilityResponse getAvailabilityResponseTrue() {
        return AvailabilityResponse.builder()
                .room(getRoomDto(23L))
                .availability(getAvailabilityMap())
                .build();
    }

    private Map<LocalDate, Boolean> getAvailabilityMap() {
        Map<LocalDate, Boolean> availabilityMap = new LinkedHashMap<>();
        availabilityMap.put(START_DATE, Boolean.FALSE);
        availabilityMap.put(END_DATE, Boolean.TRUE);
        return availabilityMap;
    }

    private List<ReservationDto> getReservationDtoList(List<Long> ids) {
        return ids.stream().map(this::getReservationDto)
                .collect(Collectors.toList());
    }

    private ReservationDto getReservationDto(Long id) {
        return ReservationDto.builder()
                .id(id)
                .roomIdList(List.of(23L))
                .startDate(START_DATE)
                .endDate(END_DATE)
                .totalAmount(BigDecimal.valueOf(20))
                .status(ReservationStatus.CREATED)
                .build();
    }

    private List<Reservation> getReservationList(List<Long> ids) {
        return ids.stream().map(this::getReservation)
                .collect(Collectors.toList());
    }

    private Reservation getReservation(Long id) {
        return Reservation.builder()
                .id(id)
                .roomList(List.of(getRoom(23L)))
                .startDate(START_DATE)
                .endDate(END_DATE)
                .status(ReservationStatus.CREATED)
                .build();
    }

    private RoomDto getRoomDto(Long id) {
        return RoomDto.builder()
                .id(id)
                .roomName("first room")
                .currentPrice(BigDecimal.TEN)
                .build();
    }


    private Room getRoom(Long id) {
        return Room.builder()
                .id(id)
                .roomName("first room")
                .currentPrice(BigDecimal.TEN)
                .build();
    }
}