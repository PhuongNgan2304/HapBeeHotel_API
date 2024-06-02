package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactModel implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phoneNo;
    private String content;
    private int status;
}
