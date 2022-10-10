package com.wandoo.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wandoo.hotel.model.ReservationDto;
import com.wandoo.hotel.model.RoomDto;
import com.wandoo.hotel.model.request.ReservationRequest;
import com.wandoo.hotel.model.response.AvailabilityResponse;
import com.wandoo.hotel.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyMap;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ReservationController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationControllerTest {

    private static final LocalDate START_DATE = LocalDate.of(2022, 10, 1);
    private static final LocalDate END_DATE = LocalDate.of(2022, 10, 10);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @Test
    void shouldReturnAllReservationInPeriod() throws Exception {
        given(reservationService.getAllReservationsWithinPeriod(START_DATE, END_DATE)).willReturn(List.of(getReservationDto()));

        mockMvc.perform(
                        get("/reservations/all")
                                .header("Origin", "http://localhost")
                                .queryParam("fromDate", START_DATE.toString())
                                .queryParam("toDate", END_DATE.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(getReservationDto()))));
    }

    @Test
    void shouldReturnPeriodAvailability() throws Exception {
        given(reservationService.checkAllRoomAvailability(START_DATE, END_DATE)).willReturn(getAvailabilityResponse());

        mockMvc.perform(
                        get("/reservations/availability")
                                .header("Origin", "http://localhost")
                                .queryParam("fromDate", START_DATE.toString())
                                .queryParam("toDate", END_DATE.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getAvailabilityResponse())));
    }

    @Test
    void shouldSaveReservation() throws Exception {
        given(reservationService.saveReservation(getReservationRequest())).willReturn(getReservationDto());

        mockMvc.perform(
                        post("/reservations/reserve")
                                .header("Origin", "http://localhost")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getReservationRequest()))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(getReservationDto())));
    }

    private ReservationRequest getReservationRequest() {
        return ReservationRequest.builder()
                .userId(1L)
                .roomIds(List.of(1L, 2L))
                .fromDate(START_DATE)
                .toDate(END_DATE)
                .build();
    }

    private ReservationDto getReservationDto() {
        return ReservationDto.builder()
                .id(12L)
                .build();
    }

    private List<AvailabilityResponse> getAvailabilityResponse() {
        return List.of(AvailabilityResponse.builder()
                .room(RoomDto.builder().build())
                .availability(emptyMap())
                .build());
    }
}