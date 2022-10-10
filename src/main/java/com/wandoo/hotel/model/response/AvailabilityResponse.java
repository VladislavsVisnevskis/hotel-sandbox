package com.wandoo.hotel.model.response;

import com.wandoo.hotel.model.RoomDto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;


public class AvailabilityResponse {

    private RoomDto room;
    private Map<LocalDate, Boolean> availability;

    public AvailabilityResponse(Builder b) {
        this.room = b.room;
        this.availability = b.availability;
    }

    public static Builder builder() {
        return new Builder();
    }

    public RoomDto getRoom() {
        return room;
    }

    public Map<LocalDate, Boolean> getAvailability() {
        return availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvailabilityResponse)) return false;
        AvailabilityResponse that = (AvailabilityResponse) o;
        return Objects.equals(getRoom(), that.getRoom())
                && Objects.equals(getAvailability(), that.getAvailability());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoom(), getAvailability());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AvailabilityResponse.class.getSimpleName() + "[", "]")
                .add("room=" + room)
                .add("availability=" + availability)
                .toString();
    }

    public static final class Builder {
        private RoomDto room;
        private Map<LocalDate, Boolean> availability;

        private Builder() {
        }

        public Builder room(RoomDto room) {
            this.room = room;
            return this;
        }

        public Builder availability(Map<LocalDate, Boolean> availability) {
            this.availability = availability;
            return this;
        }

        public AvailabilityResponse build() {
            return new AvailabilityResponse(this);
        }
    }
}
