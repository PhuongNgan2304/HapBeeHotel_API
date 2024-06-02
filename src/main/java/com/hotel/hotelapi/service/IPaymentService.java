package com.hotel.hotelapi.service;

import com.hotel.hotelapi.model.PaymentModel;

import java.time.LocalDateTime;
import java.util.List;

public interface IPaymentService {
    List<PaymentModel> getAllPayments();
    double calculateRevenue(LocalDateTime startDate, LocalDateTime endDate, Integer branchId, Integer roomTypeId);

    PaymentModel getPaymentById(Integer id);

    PaymentModel completePayment(Integer id);
}
