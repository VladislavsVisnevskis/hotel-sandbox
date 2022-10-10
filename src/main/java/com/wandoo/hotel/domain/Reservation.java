package com.wandoo.hotel.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    private static final long serialVersionUID = -878461660074584189L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User guest;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "room_reservation",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private List<Room> rooms = new ArrayList<>();

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus status;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "details")
    private String details;

    public Reservation() {
    }

    public Reservation(Builder b) {
        super(b);
        this.guest = b.guest;
        this.rooms = b.rooms;
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

    public User getGuest() {
        return guest;
    }

    public List<Room> getRooms() {
        return rooms;
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
    public int hashCode() {
        return Objects.hash(getGuest(), getRooms(), getStartDate(), getEndDate(), getStatus(), getDiscount(), getTotalAmount(), getDetails());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Reservation.class.getSimpleName() + "[", "]")
                .add("guest=" + guest)
                .add("rooms=" + rooms)
                .add("startDate=" + startDate)
                .add("endDate=" + endDate)
                .add("status=" + status)
                .add("discount=" + discount)
                .add("totalAmount=" + totalAmount)
                .add("details='" + details + "'")
                .add("id=" + id)
                .toString();
    }

    public static class Builder extends BaseEntity.Builder<Builder> {

        private User guest;
        private List<Room> rooms;
        private LocalDate startDate;
        private LocalDate endDate;
        private ReservationStatus status;
        private BigDecimal discount;
        private BigDecimal totalAmount;
        private String details;

        public Builder guest(User guest) {
            this.guest = guest;
            return this;
        }

        public Builder roomList(List<Room> roomList) {
            this.rooms = roomList;
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

        public Reservation build() {
            return new Reservation(this);
        }
    }
}
