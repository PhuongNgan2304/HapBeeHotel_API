package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 2c31b00 (update commit)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceModel {
    private int id;
    private String name;
    private double price;
<<<<<<< HEAD
    private double salePercent;
    private String description;
    private String image;
=======
    private String description;
    private List<String> imageURLs;
    private boolean isDeleted;
>>>>>>> 2c31b00 (update commit)
}
