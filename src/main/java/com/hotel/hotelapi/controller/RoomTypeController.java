package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.RoomTypeModel;
import com.hotel.hotelapi.service.IRoomTypeService;
import com.hotel.hotelapi.service.RoomTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room-type")
public class RoomTypeController {
    @Autowired
    private IRoomTypeService roomTypeService = new RoomTypeServiceImpl();
    @GetMapping("/all")
    private ResponseEntity<List<RoomTypeModel>> getAll(){
        return ResponseEntity.ok(roomTypeService.findAllActive());
    }

    @GetMapping("/{id}")
    private ResponseEntity<RoomTypeModel> getById(@PathVariable int id){
        return ResponseEntity.ok(roomTypeService.findById(id));
    }
}
