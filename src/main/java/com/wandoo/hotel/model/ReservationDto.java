package com.wandoo.hotel.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.wandoo.hotel.domain.ReservationStatus;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = ReservationDto.Builder.class)
public class ReservationDto {

    private Long id;
    private Long guestId;
    private List<Long> roomIdList;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private ReservationStatus status;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private String details;

    public ReservationDto(Builder b) {
        this.id = b.id;
        this.guestId = b.guestId;
        this.roomIdList = b.roomIdList;
        this.startDate = b.startDate;
        this.endDate = b.endDate;
        this.status = b.status;
        this.discount = b.discount;
        this.totalAmount = b.totalAmount;
        this.details = b.details;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public List<Long> getRoomIdList() {
        return roomIdList;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDto)) return false;
        ReservationDto that = (ReservationDto) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getGuestId(), that.getGuestId())
                && Objects.equals(getRoomIdList(), that.getRoomIdList())
                && Objects.equals(getStartDate(), that.getStartDate())
                && Objects.equals(getEndDate(), that.getEndDate())
                && getStatus() == that.getStatus()
                && Objects.equals(getDiscount(), that.getDiscount())
                && Objects.equals(getTotalAmount(), that.getTotalAmount())
                && Objects.equals(getDetails(), that.getDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGuestId(), getRoomIdList(), getStartDate(), getEndDate(), getStatus(), getDiscount(), getTotalAmount(), getDetails());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ReservationDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("guestId=" + guestId)
                .add("roomIdList=" + roomIdList)
                .add("startDate=" + startDate)
                .add("endDate=" + endDate)
                .add("status=" + status)
                .add("discount=" + discount)
                .add("totalAmount=" + totalAmount)
                .add("details='" + details + "'")
                .toString();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private Long id;
        private Long guestId;
        private List<Long> roomIdList;
        private LocalDate startDate;
        private LocalDate endDate;
        private ReservationStatus status;
        private BigDecimal discount;
        private BigDecimal totalAmount;
        private String details;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder guestId(Long guestId) {
            this.guestId = guestId;
            return this;
        }

        public Builder roomIdList(List<Long> roomIdList) {
            this.roomIdList = roomIdList;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder status(ReservationStatus status) {
            this.status = status;
            return this;
        }

        public Builder discount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public Builder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public ReservationDto build() {
            return new ReservationDto(this);
        }
    }
}
