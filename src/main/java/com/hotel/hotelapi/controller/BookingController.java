package com.hotel.hotelapi.controller;

<<<<<<< HEAD
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import com.hotel.hotelapi.model.BookingRequestDTO;
import com.hotel.hotelapi.model.BookingResponseDTO;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.service.BookingServiceImpl;
import com.hotel.hotelapi.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
>>>>>>> 2c31b00 (update commit)

@RestController
@RequestMapping("/api/booking")
public class BookingController {
<<<<<<< HEAD
=======
    @Autowired
    IBookingService bookingService = new BookingServiceImpl();
    @PostMapping("/reserveRoom")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        BookingResponseDTO bookingResponseDTO = bookingService.createBooking(bookingRequestDTO);
        return ResponseEntity.ok(bookingResponseDTO);
    }
>>>>>>> 2c31b00 (update commit)
}
