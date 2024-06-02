package com.hotel.hotelapi.controller;

import com.google.api.Http;
import com.hotel.hotelapi.model.BranchModel;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.model.RoomModel;
import com.hotel.hotelapi.model.RoomTypeModel;
import com.hotel.hotelapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management/room-type")
public class AdminRoomTypeController {
    @Autowired
    private IRoomTypeService roomTypeService = new RoomTypeServiceImpl();
    @Autowired
    private IRoomService roomService = new RoomServiceImpl();

    @Autowired
    private IBranchService branchService = new BranchServiceImpl();

    @PostMapping("create")
    private ResponseEntity<RoomTypeModel> createRoomType(RoomTypeModel roomTypeModel){
        return ResponseEntity.ok(roomTypeService.saveRoom(roomTypeModel));
    }

    @GetMapping("/all")
    private ResponseEntity<List<RoomTypeModel>> getAll(){
        return ResponseEntity.ok(roomTypeService.findAllActive());
    }

    @GetMapping("/{id}")
    private ResponseEntity<RoomTypeModel> getByID(@PathVariable("id") int id){
        return ResponseEntity.ok(roomTypeService.findById(id));
    }

    @GetMapping("{id}/list-branch")
    private ResponseEntity<List<BranchModel>> getBranchListByRoomType(
            @PathVariable("id") int id
    ){
        return ResponseEntity.ok(branchService.findAllByRoomType(id));
    }
    @GetMapping("/list-room")
    private ResponseEntity<List<RoomModel>> getRoomListById(
            @RequestParam("roomTypeId") int roomTypeId,
            @RequestParam("branchId") int branchId){
        return ResponseEntity.ok(roomService.findAllByRoomTypeAndBranch(roomTypeId,branchId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateRoomType(@PathVariable("id") int id,
                                                   @ModelAttribute RoomTypeModel roomTypeModel,
                                                   @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles) {
        try {
            RoomTypeModel updatedRoomType = roomTypeService.updateRoomType(id, roomTypeModel, imageFiles);
            return ResponseEntity.ok(new Response(true, "Success!!", updatedRoomType));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(false, "Failed :" + e.getMessage(), null));
        }
    }
}
