package com.hotel.hotelapi.entity;

import com.hotel.hotelapi.model.BranchModel;
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
@Table(name="room")
public class RoomEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "branchId")
    private BranchEntity branch;

    private float aceage; //Thông số về diện tích
    private int floor; //Lầu Note: (0: Tầng G)
    private int number; //Số phòng
    private String line; //Dãy (A hoặc B)
    private int size; //Số giường


=======
    private String number; //Số phòng: A101, A102, B101, B102, A,B  chính là dãy phòng.
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;

    @ManyToOne
    @JoinColumn(name = "roomType_id")
    private RoomTypeEntity roomType;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingEntity> bookings;
>>>>>>> 2c31b00 (update commit)
}
