package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeModel {
    private int id;
    private String name;   //SingleS, TwinSingle, DoubleS, DoubleL, Family, SingleL
    private float acreage;
    private float priceEachRoom;
    private String description;
    private List<String> imageURLs;
}
