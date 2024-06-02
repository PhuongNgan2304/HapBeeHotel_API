package com.hotel.hotelapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
<<<<<<< HEAD
=======
import java.util.HashSet;
import java.util.List;
import java.util.Set;
>>>>>>> 2c31b00 (update commit)

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="branch")
public class BranchEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String location;
<<<<<<< HEAD
//    private double acreage; //Đây là thông số về diện tích
    private String image;
=======
//    private String image;
    private boolean isDeleted;
    @OneToMany (mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BranchImageEntity> images;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomEntity> rooms;

>>>>>>> 2c31b00 (update commit)
}
