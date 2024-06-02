package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.CheckInOutModel;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.model.RoomModel;
import com.hotel.hotelapi.model.RoomRequestModel;
import com.hotel.hotelapi.service.IRoomService;
import com.hotel.hotelapi.service.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/management/room")
public class AdminRoomController {
    @Autowired
    IRoomService roomService = new RoomServiceImpl();


    @PostMapping("/create")
    public Response createRoom(@RequestBody RoomModel roomModel){
        RoomModel createRoom = roomService.create(roomModel);
        return new Response(true, "Room is created successfully", createRoom);
    }

    @PutMapping("/{id}")
    public Response updateRoom(@PathVariable int id, @RequestBody RoomModel roomModel){
        RoomModel updateRoom = roomService.update(id, roomModel);
        if(updateRoom != null){
            return new Response(true, "Room is updated successfully", updateRoom);
        }
        return new Response(false, "Room is not found", null);
    }
    @DeleteMapping("/{id}")
    public Response deleteRoom(@PathVariable int id){
        boolean isDeleted = roomService.softDelete(id);
        if(isDeleted){
            return new Response(true, "Room is deleted successfully!", null);
        }
        return new Response(false, "Room is not found", null);
    }
    @GetMapping("/getEmpty")
        public Response getEmptyRoom(@RequestBody CheckInOutModel checkInOutModel){
        Response response = new Response();

//        if(roomService.checkRoomEmpty(checkInOutModel))
//            response = new Response(true,"Empty",true);
//        else
//            response = new Response(true,"Not Empty",false);
        return response;
    }
    @PostMapping("/save-room-list")
    public ResponseEntity<List<RoomModel>> saveRoomList(
            @RequestBody List<RoomRequestModel> roomRequestModels) {
        List<RoomModel> savedRooms = roomService.saveRoomList(roomRequestModels);
        return ResponseEntity.ok(savedRooms);
    }
}
