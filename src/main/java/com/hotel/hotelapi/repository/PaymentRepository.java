package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Integer> {
    Optional<PaymentEntity> findByBookingId(int bookingId);
}
