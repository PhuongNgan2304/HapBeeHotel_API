package com.hotel.hotelapi.service;

import com.hotel.hotelapi.entity.PaymentEntity;
import com.hotel.hotelapi.model.PaymentModel;
import com.hotel.hotelapi.repository.PaymenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymenServiceImpl implements IPaymentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymenRepository paymentRepository;

    @Override
    public List<PaymentModel> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentEntity -> modelMapper.map(paymentEntity, PaymentModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public double calculateRevenue(LocalDateTime startDate, LocalDateTime endDate, Integer branchId, Integer roomTypeId) {
        return paymentRepository.calculateRevenue(startDate, endDate, branchId, roomTypeId);
    }

    @Override
    public PaymentModel getPaymentById(Integer id) {
        PaymentEntity payment = paymentRepository.findById(id).orElse(null);
        return modelMapper.map(payment, PaymentModel.class);
    }
    @Override
    public PaymentModel completePayment(Integer id){
        PaymentEntity payment = paymentRepository.findById(id).orElse(null);
        payment.setPaymentStatus("completed");
        paymentRepository.save(payment);
        return modelMapper.map(payment,PaymentModel.class);
    }
}
