package com.hotel.hotelapi.service;

import com.hotel.hotelapi.model.PaymentModel;
import com.hotel.hotelapi.model.RoomTypeModel;
import com.hotel.hotelapi.user.ChangePasswordRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

public interface IUserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
    PaymentModel payBooking(int bookingId, String paymentMethod);
    RoomTypeModel addRoomToCart(int roomTypeId);
    String removeRoomFromCart(int roomTypeId);
}
