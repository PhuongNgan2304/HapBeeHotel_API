package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.PaymentModel;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.service.IPaymentService;
import com.hotel.hotelapi.service.PaymenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    IPaymentService paymentService = new PaymenServiceImpl();

    @GetMapping("")
    public ResponseEntity<Response> getAllPayments() {
        try {
            List<PaymentModel> paymentModelList = paymentService.getAllPayments();
            return ResponseEntity.ok(new Response(true, "Get successfully", paymentModelList));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Failed to get payments", null));
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<Response> getRevenue(
            @RequestParam(value = "startDate") LocalDateTime startDate,
            @RequestParam(value = "endDate") LocalDateTime endDate,
            @RequestParam(value = "branchId", required = false) Integer branchId,
            @RequestParam(value = "roomTypeId", required = false) Integer roomTypeId) {
        try {
            double revenue = paymentService.calculateRevenue(startDate, endDate, branchId, roomTypeId);
            return ResponseEntity.ok(new Response(true, "Revenue calculated successfully", revenue));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Failed to calculate revenue", null));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getPaymentById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(new Response(true, "Get successfully", paymentService.getPaymentById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> completePayment(@PathVariable("id") Integer id){
        return ResponseEntity.ok(new Response(true, "Complete payment", paymentService.completePayment(id)));
    }
}
