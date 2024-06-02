package com.hotel.hotelapi.model;

import com.hotel.hotelapi.entity.BookingEntity;
import com.hotel.hotelapi.entity.ServiceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingServiceModel {
    private int id;
    //private BookingEntity booking;
    //private ServiceModel service;
    private int service_id;
    private String name;
    private double price;
    //private String description;
    private boolean isDeleted;
}
