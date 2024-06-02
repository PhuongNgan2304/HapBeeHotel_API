package com.hotel.hotelapi.service;

import com.hotel.hotelapi.model.RoomTypeModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRoomTypeService {
    RoomTypeModel findByIdActive(int id);
    RoomTypeModel findByIdInactive(int id);
    List<RoomTypeModel> findAllActive();
    List<RoomTypeModel> findAllInactive();
    //BranchModel findByLocation(String location);
    RoomTypeModel findById(int id);
    List<RoomTypeModel> findAll();

    RoomTypeModel saveRoom(RoomTypeModel roomTypeModel);
    RoomTypeModel updateRoomType(int id, RoomTypeModel roomTypeModel, List<MultipartFile> imageFiles);
}
