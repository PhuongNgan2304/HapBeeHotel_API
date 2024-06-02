package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.CartRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRoomRepository extends JpaRepository<CartRoomEntity,Integer> {
    List<CartRoomEntity> findAllByUserEmail(String email);
}
