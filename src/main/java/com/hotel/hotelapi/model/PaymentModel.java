package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel implements Serializable {
    private int id;
    private BookingResponseDTO booking;
    private String paymentMethod;
    private double totalMoney;
    private String paymentStatus;
    private LocalDateTime timestamp;
}
