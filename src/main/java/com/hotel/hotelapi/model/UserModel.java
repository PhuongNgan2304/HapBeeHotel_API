package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private int id;
    private String fullname;
    private String email;
    private LocalDate birthday;
    private String address;
    private String phone;
    private LocalDateTime createDay;
    private boolean active;
}
