package com.hotel.hotelapi.service;

import com.hotel.hotelapi.model.BookingBranchResponseDTO;
import com.hotel.hotelapi.model.BookingRequestDTO;
import com.hotel.hotelapi.model.BookingResponseDTO;

import java.util.List;

public interface IBookingService {
    BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO);
    List<BookingBranchResponseDTO> getBookingsByBranch(int branchId);
}
