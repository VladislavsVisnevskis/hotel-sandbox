package com.wandoo.hotel.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = ReservationRequest.Builder.class)
public class ReservationRequest {

    @NotNull
    private Long userId;

    @NotNull
    @Size(min = 1)
    private List<Long> roomIds;

    @NotNull
    @FutureOrPresent
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @NotNull
    @FutureOrPresent
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    private String description;

    public ReservationRequest(Builder b) {
        this.userId = b.userId;
        this.roomIds = b.roomIds;
        this.fromDate = b.fromDate;
        this.toDate = b.toDate;
        this.description = b.description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getUserId() {
        return userId;
    }

    public List<Long> getRoomIds() {
        return roomIds;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationRequest)) return false;
        ReservationRequest that = (ReservationRequest) o;
        return Objects.equals(getUserId(), that.getUserId())
                && Objects.equals(getRoomIds(), that.getRoomIds())
                && Objects.equals(getFromDate(), that.getFromDate())
                && Objects.equals(getToDate(), that.getToDate())
                && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getRoomIds(), getFromDate(), getToDate(), getDescription());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ReservationRequest.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("roomIds=" + roomIds)
                .add("fromDate=" + fromDate)
                .add("toDate=" + toDate)
                .add("description='" + description + "'")
                .toString();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private Long userId;
        private List<Long> roomIds;
        private LocalDate fromDate;
        private LocalDate toDate;
        private String description;

        private Builder() {
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder roomIds(List<Long> roomIds) {
            this.roomIds = roomIds;
            return this;
        }

        public Builder fromDate(LocalDate fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public Builder toDate(LocalDate toDate) {
            this.toDate = toDate;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public ReservationRequest build() {
            return new ReservationRequest(this);
        }
    }
}
