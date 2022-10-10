package com.wandoo.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wandoo.hotel.domain.UserType;
import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = UserDto.Builder.class)
public class UserDto {

    private Long id;
    private String firstName;
    private String secondName;
    private UserType type;
    private String email;
    private String phoneNumber;
    private String address;
    private List<ReservationDto> reservationList;

    public UserDto(Builder b) {
        this.id = b.id;
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

    public Long getId() {
        return id;
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

    public List<ReservationDto> getReservationList() {
        return reservationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(getId(), userDto.getId())
                && Objects.equals(getFirstName(), userDto.getFirstName())
                && Objects.equals(getSecondName(), userDto.getSecondName())
                && getType() == userDto.getType()
                && Objects.equals(getEmail(), userDto.getEmail())
                && Objects.equals(getPhoneNumber(), userDto.getPhoneNumber())
                && Objects.equals(getAddress(), userDto.getAddress())
                && Objects.equals(getReservationList(), userDto.getReservationList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getSecondName(), getType(), getEmail(), getPhoneNumber(), getAddress(), getReservationList());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("secondName='" + secondName + "'")
                .add("type=" + type)
                .add("email='" + email + "'")
                .add("phoneNumber='" + phoneNumber + "'")
                .add("address='" + address + "'")
                .add("reservationList=" + reservationList)
                .toString();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private Long id;
        private String firstName;
        private String secondName;
        private UserType type;
        private String email;
        private String phoneNumber;
        private String address;
        private List<ReservationDto> reservationList;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id= id;
            return this;
        }

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

        public Builder reservationList(List<ReservationDto> reservationList) {
            this.reservationList = reservationList;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
