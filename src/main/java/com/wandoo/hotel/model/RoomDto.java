package com.wandoo.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wandoo.hotel.domain.RoomType;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = RoomDto.Builder.class)
public class RoomDto {

    private Long id;
    @NotNull
    private String roomName;
    @NotNull
    private RoomType roomType;
    @NotNull
    private BigDecimal currentPrice;
    private String description;
    private Set<ReservationDto> reservationDtoSet;

    public RoomDto(Builder b) {
        this.id = b.id;
        this.roomName = b.roomName;
        this.roomType = b.roomType;
        this.currentPrice = b.currentPrice;
        this.description = b.description;
        this.reservationDtoSet = b.reservations;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public String getDescription() {
        return description;
    }

    public Set<ReservationDto> getReservationDtoSet() {
        return reservationDtoSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomDto)) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(getId(), roomDto.getId())
                && Objects.equals(getRoomName(), roomDto.getRoomName())
                && getRoomType() == roomDto.getRoomType()
                && Objects.equals(getCurrentPrice(), roomDto.getCurrentPrice())
                && Objects.equals(getDescription(), roomDto.getDescription())
                && Objects.equals(getReservationDtoSet(), roomDto.getReservationDtoSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoomName(), getRoomType(), getCurrentPrice(), getDescription(), getReservationDtoSet());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RoomDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("roomName='" + roomName + "'")
                .add("roomType=" + roomType)
                .add("currentPrice=" + currentPrice)
                .add("description='" + description + "'")
                .add("reservationDtoSet=" + reservationDtoSet)
                .toString();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private Long id;
        private String roomName;
        private RoomType roomType;
        private BigDecimal currentPrice;
        private String description;
        private Set<ReservationDto> reservations;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder roomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public Builder roomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public Builder currentPrice(BigDecimal currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder reservationList(Set<ReservationDto> reservations) {
            this.reservations = reservations;
            return this;
        }

        public RoomDto build() {
            return new RoomDto(this);
        }
    }
}
