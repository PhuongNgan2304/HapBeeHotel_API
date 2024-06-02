
package com.hotel.hotelapi.model;

import com.hotel.hotelapi.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private int bookingId;
    private int userId;
    //private UserEntity userEntity;
    private int roomId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String paymentStatus;
    private List<Integer> serviceIds = new ArrayList<>();
    private int paymentid;
}
