package com.hotel.hotelapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="rate")
public class RateEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "bookId")
    private BookEntity book;
=======
//    @ManyToOne
//    @JoinColumn(name = "orderId")
//    private OrderEntity order;
>>>>>>> 2c31b00 (update commit)

    private int star; //1-5
    private String comment;
}
