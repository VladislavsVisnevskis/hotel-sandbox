package com.wandoo.hotel.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {

    private static final long serialVersionUID = -2133171978240956419L;

    @Column(name = "room_name")
    private String roomName;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    @Column(name = "description")
    private String description;

    @JsonBackReference
    @ManyToMany(mappedBy = "rooms", fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<>();

    public Room() {
    }

    public Room(Builder b) {
        super(b);
        this.roomName = b.roomName;
        this.roomType = b.roomType;
        this.currentPrice = b.currentPrice;
        this.description = b.description;
        this.reservations = b.reservations;
    }

    public static Builder builder() {
        return new Builder();
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

    public Set<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(getRoomName(), room.getRoomName())
                && getRoomType() == room.getRoomType()
                && Objects.equals(getCurrentPrice(), room.getCurrentPrice())
                && Objects.equals(getDescription(), room.getDescription())
                && Objects.equals(getReservations(), room.getReservations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomName(), getRoomType(), getCurrentPrice(), getDescription(), getReservations());
    }

    public static final class Builder extends BaseEntity.Builder<Builder> {
        private String roomName;
        private RoomType roomType;
        private BigDecimal currentPrice;
        private String description;
        private Set<Reservation> reservations;

        private Builder() {
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

        public Builder reservations(Set<Reservation> reservations) {
            this.reservations = reservations;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}
