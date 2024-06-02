// BookingRequestDTO.java
package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {
    private int userId;
    private int roomId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
//    private String paymentStatus;
    private List<Integer> serviceIds; // List of service IDs associated with this booking
    private String paymentMethod;
    private int total_money;
}
