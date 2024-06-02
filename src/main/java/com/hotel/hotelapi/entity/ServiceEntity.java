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
@NoArgsConstructor
@AllArgsConstructor
@Table(name="service")
public class ServiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;
<<<<<<< HEAD
    private double salePercent; //Phan tram giam gia
    private String description;
    private String image;
=======
    private String description;
    private boolean isDeleted;


    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceImageEntity> images;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingServiceEntity> booking_services;

>>>>>>> 2c31b00 (update commit)
}
