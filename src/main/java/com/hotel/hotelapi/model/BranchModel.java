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
public class BranchModel {
    private int id;
    private String location;
<<<<<<< HEAD
    private String image;
=======
    private boolean isDeleted;
    private List<String> imageURLs;
>>>>>>> 2c31b00 (update commit)
}
