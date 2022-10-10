package com.wandoo.hotel.controller;

import com.wandoo.hotel.exception.ServiceException;
import com.wandoo.hotel.model.ReservationDto;
import com.wandoo.hotel.model.request.ReservationRequest;
import com.wandoo.hotel.model.response.AvailabilityResponse;
import com.wandoo.hotel.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    @Operation(summary = "List all reservation for provided period if user has ADMIN rights")
    @ApiResponse(responseCode = "200", description = "List of reservation per period", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationDto.class)))})
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public List<ReservationDto> getReservationsWithinPeriod(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                      @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return reservationService.getAllReservationsWithinPeriod(fromDate, toDate);
    }

    @PreAuthorize("hasAuthority('GUEST') or hasAuthority('ADMIN')")
    @GetMapping("/availability")
    @Operation(summary = "All room availability for specified period")
    @ApiResponse(responseCode = "200", description = "Room availability", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AvailabilityResponse.class)))})
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public List<AvailabilityResponse> getPeriodAvailability(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return reservationService.checkAllRoomAvailability(fromDate, toDate);
    }

    @PreAuthorize("hasAuthority('GUEST') or hasAuthority('ADMIN')")
    @PostMapping("/reserve")
    @Operation(summary = "All room availability for specified period")
    @ApiResponse(responseCode = "201", description = "Room availability", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AvailabilityResponse.class)))})
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<ReservationDto> saveReservation(@RequestBody @Validated ReservationRequest reservationRequest) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.saveReservation(reservationRequest));
    }

}
