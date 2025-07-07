package com.example.springboot.User.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private Long id;
    @NonNull
    private Long userId;
    @NotNull
    private String name;
    private Double latitude;
    private Double longitude;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @NotNull
    private String description;

    @Override
    public String toString() {
        return "AddressDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
