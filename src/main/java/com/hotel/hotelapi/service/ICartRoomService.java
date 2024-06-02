package com.hotel.hotelapi.service;

import com.hotel.hotelapi.model.RoomTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ICartRoomService{
    public List<RoomTypeModel> getCartRoomByToken();
}
