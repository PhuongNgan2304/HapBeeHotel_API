package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchModel {
    private int id;
    private String location;
    private String image;
    private boolean isDeleted;
    private List<String> imageURLs;
}
