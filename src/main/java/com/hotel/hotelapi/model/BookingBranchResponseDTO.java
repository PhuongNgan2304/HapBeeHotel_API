package com.hotel.hotelapi.model;


import com.hotel.hotelapi.entity.BookingServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingBranchResponseDTO {
    private String branchName;
    private int bookingId;
    private UserModel userModel;
    private RoomModel roomModel;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String paymentStatus;
    private List<BookingServiceModel> bookingServiceEntityList;
    private PaymentModel paymentModel;
}
