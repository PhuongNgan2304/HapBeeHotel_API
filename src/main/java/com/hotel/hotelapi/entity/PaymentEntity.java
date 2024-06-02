    package com.hotel.hotelapi.entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.io.Serializable;
    import java.time.LocalDateTime;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class PaymentEntity implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @OneToOne
        @JoinColumn(name = "booking_id")
        private BookingEntity booking;

        private String paymentMethod;
//        private String paymentInfor;
        private double totalMoney;
        private LocalDateTime timestamp;
        private String paymentStatus;
    }
