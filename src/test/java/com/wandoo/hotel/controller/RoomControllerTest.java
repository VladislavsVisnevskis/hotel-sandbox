package com.wandoo.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wandoo.hotel.domain.RoomType;
import com.wandoo.hotel.model.RoomDto;
import com.wandoo.hotel.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RoomController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomService roomService;

    @Test
    void shouldReturnAllRooms() throws Exception {
        given(roomService.getAllRooms()).willReturn(getRoomList());

        mockMvc.perform(
                        get("/rooms/all")
                                .header("Origin", "http://localhost")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getRoomList())));
    }

    @Test
    void shouldReturnRoomById() throws Exception {
        given(roomService.getRoomById(1L)).willReturn(getRoomDto());

        mockMvc.perform(
                        get("/rooms/1")
                                .header("Origin", "http://localhost")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getRoomDto())));
    }

    @Test
    void shouldSaveRoom() throws Exception {
        given(roomService.saveRoom(getRoomDtoRequest())).willReturn(getRoomDtoResponse());

        mockMvc.perform(
                        post("/rooms/save")
                                .header("Origin", "http://localhost")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getRoomDtoRequest()))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(getRoomDtoResponse())));
    }

    @Test
    void shouldEditRoom() throws Exception {
        mockMvc.perform(
                        patch("/rooms/1")
                                .header("Origin", "http://localhost")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getRoomDtoRequest()))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(roomService).editRoom(1L, getRoomDtoRequest());

    }

    private RoomDto getRoomDto() {
        return RoomDto.builder()
                .id(669L)
                .build();
    }

    private RoomDto getRoomDtoRequest() {
        return RoomDto.builder()
                .roomName("room")
                .roomType(RoomType.APARTMENT)
                .build();
    }

    private RoomDto getRoomDtoResponse() {
        return RoomDto.builder()
                .roomName("room")
                .roomType(RoomType.APARTMENT)
                .id(669L)
                .build();
    }

    private List<RoomDto> getRoomList() {
        return List.of(RoomDto.builder()
                .id(69L)
                .build());
    }
}