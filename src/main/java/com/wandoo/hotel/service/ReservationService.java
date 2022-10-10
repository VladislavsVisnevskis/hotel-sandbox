package com.wandoo.hotel.service;

import com.wandoo.hotel.domain.Reservation;
import com.wandoo.hotel.domain.ReservationStatus;
import com.wandoo.hotel.exception.ServiceException;
import com.wandoo.hotel.mapper.ReservationMapper;
import com.wandoo.hotel.model.ReservationDto;
import com.wandoo.hotel.model.RoomDto;
import com.wandoo.hotel.model.request.ReservationRequest;
import com.wandoo.hotel.model.response.AvailabilityResponse;
import com.wandoo.hotel.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private static Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final UserService userService;

    public ReservationService(ReservationRepository reservationRepository, RoomService roomService, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.userService = userService;
    }

    public List<ReservationDto> getAllReservationsWithinPeriod(LocalDate fromDate, LocalDate toDate) {
        logger.info("Retrieving all reservations from {} to {}", fromDate, toDate);
        List<Reservation> allWithinDateInterval = reservationRepository.findAllWithinDateInterval(fromDate, toDate);
        return allWithinDateInterval.stream()
                .map(ReservationMapper::mapToReservationDto)
                .collect(Collectors.toList());
    }

    public List<AvailabilityResponse> checkAllRoomAvailability(LocalDate fromDate, LocalDate toDate) {
        logger.info("Checking all room availability for period from {} to {}", fromDate, toDate);
        List<ReservationDto> reservationList = getAllReservationsWithinPeriod(fromDate, toDate);
        List<RoomDto> allRooms = roomService.getAllRooms();
        List<AvailabilityResponse> availabilityResponseList = new ArrayList<>();
        allRooms.forEach(room -> {
            AvailabilityResponse availabilityResponse = AvailabilityResponse.builder()
                    .room(room)
                    .availability(getAvailabilityMap(room, fromDate, toDate, reservationList))
                    .build();
            availabilityResponseList.add(availabilityResponse);
        });
        return availabilityResponseList;
    }

    public ReservationDto saveReservation(ReservationRequest reservationRequest) throws ServiceException {
        LocalDate fromDate = reservationRequest.getFromDate();
        LocalDate toDate = reservationRequest.getToDate();
        if (fromDate.isEqual(toDate)
                || fromDate.isAfter(toDate)) {
            throw new ServiceException("Illegal dates are provided");
        }
        List<RoomDto> rooms = reservationRequest.getRoomIds().stream()
                .map(roomService::getRoomById)
                .collect(Collectors.toList());
        List<ReservationDto> reservationList = getAllReservationsWithinPeriod(fromDate, toDate);
        for (RoomDto room : rooms) {
            for (LocalDate date = fromDate; date.isBefore(toDate) || date.isEqual(toDate); date = date.plusDays(1)) {
                if (isRoomReserved(room, reservationList, date)) {
                    throw new ServiceException("Reservation conflict");
                }
            }
        }
        long dayCount = ChronoUnit.DAYS.between(fromDate, toDate);
        BigDecimal totalAmount = rooms.stream().map(RoomDto::getCurrentPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(BigDecimal.valueOf(dayCount));
        Reservation reservation = Reservation.builder()
                .guest(userService.getUserById(reservationRequest.getUserId()))
                .roomList(roomService.getRoomByIds(reservationRequest.getRoomIds()))
                .totalAmount(totalAmount)
                .startDate(fromDate)
                .endDate(toDate)
                .status(ReservationStatus.CREATED)
                .details(reservationRequest.getDescription())
                .build();
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.mapToReservationDto(savedReservation);
    }

    private Map<LocalDate, Boolean> getAvailabilityMap(RoomDto room, LocalDate fromDate, LocalDate toDate, List<ReservationDto> reservationList) {
        Map<LocalDate, Boolean> availabilityMap = new LinkedHashMap<>();
        for (LocalDate date = fromDate; date.isBefore(toDate) || date.isEqual(toDate); date = date.plusDays(1)) {
            if (isRoomReserved(room, reservationList, date)) {
                availabilityMap.put(date, Boolean.FALSE);
            } else {
                availabilityMap.put(date, Boolean.TRUE);
            }
        }
        return availabilityMap;
    }

    private boolean isRoomReserved(RoomDto room, List<ReservationDto> reservationList, LocalDate date) {
        return reservationList.stream().anyMatch(reservation -> reservation.getRoomIdList().contains(room.getId())
                && (date.isEqual(reservation.getStartDate()) || date.isAfter(reservation.getStartDate()))
                && date.isBefore(reservation.getEndDate()));
    }
}
