package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInOutModel {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
