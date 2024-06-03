package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.BookingRequestDTO;
import com.hotel.hotelapi.model.BookingResponseDTO;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.service.BookingServiceImpl;
import com.hotel.hotelapi.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    IBookingService bookingService = new BookingServiceImpl();
    @PostMapping("/reserveRoom")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        BookingResponseDTO bookingResponseDTO = bookingService.createBooking(bookingRequestDTO);
        return ResponseEntity.ok(bookingResponseDTO);
    }
}
