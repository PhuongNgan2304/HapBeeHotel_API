package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.RoomAvailableRequest;
import com.hotel.hotelapi.model.RoomModel;
import com.hotel.hotelapi.repository.RoomRepository;
import com.hotel.hotelapi.service.IRoomService;
import com.hotel.hotelapi.service.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    @Autowired
    private IRoomService roomService = new RoomServiceImpl();
    @GetMapping("/branch-roomtype")
    private ResponseEntity<List<RoomModel>> getRoomListById(
            @RequestParam("roomTypeId") int roomTypeId,
            @RequestParam("branchId") int branchId){
        return ResponseEntity.ok(roomService.findAllByRoomTypeAndBranch(roomTypeId,branchId));
    }
    @GetMapping("/available")
    public ResponseEntity<List<RoomModel>> findAvailableRooms(
            @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkIn,
            @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOut,
            @RequestParam("branchId") int branchId,
            @RequestParam("roomTypeId") int roomTypeId){
        List<RoomModel> availableRoomModels = roomService.findAvailableRoomModels(roomTypeId,branchId,checkIn,checkOut);
        return ResponseEntity.ok(availableRoomModels);
    }
}
