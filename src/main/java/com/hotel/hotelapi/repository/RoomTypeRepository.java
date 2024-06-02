package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.RoomEntity;
import com.hotel.hotelapi.entity.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity,Integer> {
    List<RoomTypeEntity> findAllByIsDeletedFalse();
    List<RoomTypeEntity> findAllByIsDeletedTrue();
    Optional<RoomTypeEntity> findByIdAndIsDeletedFalse(Integer integer);
    Optional<RoomTypeEntity> findByIdAndIsDeletedTrue(Integer integer);
}
