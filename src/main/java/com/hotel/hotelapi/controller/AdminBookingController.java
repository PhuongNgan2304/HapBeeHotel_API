package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.BookingBranchResponseDTO;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.model.ServiceModel;
import com.hotel.hotelapi.service.BookingServiceImpl;
import com.hotel.hotelapi.service.BranchServiceImpl;
import com.hotel.hotelapi.service.IBookingService;
import com.hotel.hotelapi.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management/booking")
public class AdminBookingController {
    //API thống kê booking theo branch
    @Autowired
    IBookingService bookingService = new BookingServiceImpl();

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<BookingBranchResponseDTO>> getBookingsByBranch(@PathVariable int branchId) {
        try {
            List<BookingBranchResponseDTO> bookings = bookingService.getBookingsByBranch(branchId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            // Return a 500 Internal Server Error status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
