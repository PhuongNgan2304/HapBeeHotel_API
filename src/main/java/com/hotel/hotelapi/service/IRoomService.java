package com.hotel.hotelapi.service;

<<<<<<< HEAD
import com.hotel.hotelapi.model.BranchModel;
import com.hotel.hotelapi.model.RoomModel;

import java.util.List;

public interface IRoomService {
    RoomModel findById(int id);
=======
import com.hotel.hotelapi.model.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {

    List<RoomModel> findAllByRoomTypeAndBranch(int Rooomid,int branchId);
>>>>>>> 2c31b00 (update commit)
    List<RoomModel> findAll();
    RoomModel create (RoomModel roomModel);
    RoomModel update(int id, RoomModel RoomDTO);

<<<<<<< HEAD
    void delete(int id);

=======
    boolean softDelete(int id);

    boolean checkRoomEmpty(CheckInOutModel checkInOutModel);

    List<RoomModel> findAvailableRoomModels(int roomId, int branchId, LocalDateTime checkIn, LocalDateTime checkOut);

    List<RoomModel> saveRoomList(List<RoomRequestModel> requestModels);
>>>>>>> 2c31b00 (update commit)
}
