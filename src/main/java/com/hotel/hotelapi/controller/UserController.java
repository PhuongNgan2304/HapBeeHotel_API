package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.PaymentModel;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.model.RoomTypeModel;
import com.hotel.hotelapi.service.IUserService;
import com.hotel.hotelapi.service.UserServiceImpl;
import com.hotel.hotelapi.user.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    IUserService userService = new UserServiceImpl();
    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payBooking")
    public ResponseEntity<Response> payBooking(@RequestParam("bookingId") int bookingId,
                                               @RequestParam(value = "paymentMethod", required = false) String paymentMethod) {
        try {
            PaymentModel msg = userService.payBooking(bookingId, paymentMethod);
            return ResponseEntity.ok(new Response(true, "Success!!", msg));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(false, "Failed: " + e.getMessage(), null));
        }
    }

    @PostMapping("/addRoomToCart/{id}")
    public ResponseEntity<Response> addRoomToCart(@PathVariable("id") int roomTypeId) {
        try {
            RoomTypeModel roomTypeModel = userService.addRoomToCart(roomTypeId);
            return ResponseEntity.ok(new Response(true, "Add to cart successfully", roomTypeModel));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(false, "Failed: " + e.getMessage(), null));
        }
    }

    @PostMapping("/removeRoomFromCart/{id}")
    public ResponseEntity<Response> removeRoomFromCart(@PathVariable("id") int roomTypeId) {
        try {
            String msg = userService.removeRoomFromCart(roomTypeId);
            return ResponseEntity.ok(new Response(true, "Remove room successfully", msg));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(false, "Failed: " + e.getMessage(), null));
        }
    }
}
