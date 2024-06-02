package com.hotel.hotelapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="roomtype")
public class RoomTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;   //SingleS, TwinSingle, DoubleS, DoubleL, Family, SingleL
    private float acreage;
    private float priceEachRoom;
    private String description;
    private boolean isDeleted;//về cơ bản thì sẽ set false hết cho các room type để cho cố định.

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomEntity> rooms;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomTypeImageEntity> roomTypeImages;


//    @OneToMany (mappedBy = "roomtype")
//    private List<Branch_RoomTypeEntity> branch_roomTypeList;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartRoomEntity> carts;

}
