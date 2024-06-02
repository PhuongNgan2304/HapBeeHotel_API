package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.RoomTypeModel;
import com.hotel.hotelapi.service.CartRoomServiceImpl;
import com.hotel.hotelapi.service.ICartRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartRoomController {
    @Autowired
    private ICartRoomService cartRoomService = new CartRoomServiceImpl();
    @GetMapping("/all")
    private ResponseEntity<List<RoomTypeModel>> getCartRooms(){
        return ResponseEntity.ok(cartRoomService.getCartRoomByToken());
    }
}
