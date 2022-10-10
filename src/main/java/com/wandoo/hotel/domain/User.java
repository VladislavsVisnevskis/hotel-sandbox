package com.wandoo.hotel.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private static final long serialVersionUID = -6790723790738956392L;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String secondName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType type;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservationList;

    public User() {
    }

    public User(Builder b) {
        super(b);
        this.firstName = b.firstName;
        this.secondName = b.secondName;
        this.type = b.type;
        this.email = b.email;
        this.phoneNumber = b.phoneNumber;
        this.address = b.address;
        this.reservationList = b.reservationList;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public UserType getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getFirstName(), user.getFirstName())
                && Objects.equals(getSecondName(), user.getSecondName())
                && getType() == user.getType()
                && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getPhoneNumber(), user.getPhoneNumber())
                && Objects.equals(getAddress(), user.getAddress())
                && Objects.equals(getReservationList(), user.getReservationList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getSecondName(), getType(), getEmail(), getPhoneNumber(), getAddress(), getReservationList());
    }

    public static final class Builder extends BaseEntity.Builder<User.Builder> {
        private String firstName;
        private String secondName;
        private UserType type;
        private String email;
        private String phoneNumber;
        private String address;
        private List<Reservation> reservationList;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder secondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public Builder type(UserType type) {
            this.type = type;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder reservationList(List<Reservation> reservationList) {
            this.reservationList = reservationList;
            return this;
        }

        public User build() {
            User user = new User();
            user.phoneNumber = this.phoneNumber;
            user.type = this.type;
            user.address = this.address;
            user.firstName = this.firstName;
            user.email = this.email;
            user.reservationList = this.reservationList;
            user.id = this.id;
            user.secondName = this.secondName;
            return user;
        }
    }
}
