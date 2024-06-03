package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceModel {
    private int id;
    private String name;
    private double price;
    //private double salePercent;
    private String description;
    private String image;
    private List<String> imageURLs;
    private boolean isDeleted;
}
