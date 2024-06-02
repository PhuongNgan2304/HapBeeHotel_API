<<<<<<< HEAD
package com.hotel.hotelapi.model;

import com.hotel.hotelapi.entity.BookingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentModel{
    private int id;
    private String paymentMethod;
    private int totalMoney;
    private LocalDateTime timestamp;
    private String paymentStatus;
}
=======
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
>>>>>>> 49920c3e0db1959ce24b628970dab4b37bf94900
