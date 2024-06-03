package com.hotel.hotelapi.service;

import com.hotel.hotelapi.model.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {

    List<RoomModel> findAllByRoomTypeAndBranch(int Rooomid,int branchId);
    List<RoomModel> findAll();
    RoomModel create (RoomModel roomModel);
    RoomModel update(int id, RoomModel RoomDTO);

    boolean softDelete(int id);

    boolean checkRoomEmpty(CheckInOutModel checkInOutModel);

    List<RoomModel> findAvailableRoomModels(int roomId, int branchId, LocalDateTime checkIn, LocalDateTime checkOut);

    List<RoomModel> saveRoomList(List<RoomRequestModel> requestModels);
}
