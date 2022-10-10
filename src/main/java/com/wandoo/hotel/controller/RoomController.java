package com.wandoo.hotel.controller;

import com.wandoo.hotel.model.RoomDto;
import com.wandoo.hotel.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    @Operation(summary = "List all rooms")
    @ApiResponse(responseCode = "200", description = "All rooms", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoomDto.class)))})
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{roomId}")
    @Operation(summary = "List all rooms")
    @ApiResponse(responseCode = "200", description = "Room by id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoomById(roomId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    @Operation(summary = "Save room")
    @ApiResponse(responseCode = "201", description = "Room is saved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<RoomDto> saveRoom(@RequestBody @Validated final RoomDto room) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.saveRoom(room));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{roomId}")
    @Operation(summary = "Edit existing room")
    @ApiResponse(responseCode = "200", description = "Room is updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<Void> updateRoom(@PathVariable Long roomId, @RequestBody @Validated final RoomDto room) {
        roomService.editRoom(roomId, room);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
