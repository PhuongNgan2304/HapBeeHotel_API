package com.hotel.hotelapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Normalized;

@Data
@AllArgsConstructor
@Normalized
public class AddToCartResponse {
    private RoomTypeModel roomTypeModel;
}
