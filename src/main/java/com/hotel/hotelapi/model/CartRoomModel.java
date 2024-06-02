package com.hotel.hotelapi.model;

import com.hotel.hotelapi.entity.RoomTypeEntity;
import jakarta.persistence.*;

import java.util.List;

public class CartRoomModel {
    private int id;
    private List<RoomTypeModel> roomType;
}
