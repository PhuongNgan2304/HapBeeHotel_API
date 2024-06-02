package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PaymenRepository extends JpaRepository<PaymentEntity, Integer> {
    @Query("SELECT SUM(p.totalMoney) FROM PaymentEntity p " +
            "JOIN p.booking b " +
            "JOIN b.room r " +
            "JOIN r.branch br " +
            "JOIN r.roomType rt " +
            "WHERE p.timestamp BETWEEN :startDate AND :endDate " +
            "AND (:branchId IS NULL OR br.id = :branchId) " +
            "AND (:roomTypeId IS NULL OR rt.id = :roomTypeId)")
    double calculateRevenue(LocalDateTime startDate, LocalDateTime endDate, Integer branchId, Integer roomTypeId);
}
