package com.hotel.hotelapi.repository;

<<<<<<< HEAD
import com.hotel.hotelapi.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  RoomRepository extends JpaRepository<RoomEntity,Integer> {
=======
import com.hotel.hotelapi.entity.BranchEntity;
import com.hotel.hotelapi.entity.RoomEntity;
import com.hotel.hotelapi.model.RoomModel;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface  RoomRepository extends JpaRepository<RoomEntity,Integer> {
    List<RoomEntity> findAllByIsDeletedFalse();
    List<RoomEntity> findAllByIsDeletedTrue();
    Optional<RoomEntity> findByIdAndIsDeletedFalse(Integer integer);
    Optional<RoomEntity> findByIdAndIsDeletedTrue(Integer integer);
//    List<RoomEntity> findAllByRoomTypeIdAndDeletedIsFalse(int roomTypeId);
    List<RoomEntity> findRoomEntitiesByRoomTypeIdAndBranchIdAndIsDeletedIsFalse(int roomTypeId,int isDelete);
    @Query("SELECT r FROM RoomEntity r WHERE r.roomType.id = :roomTypeId AND r.branch.id = :branchId AND r.isDeleted = false AND NOT EXISTS (SELECT b FROM r.bookings b WHERE b.room = r AND b.checkIn < :checkOut AND b.checkOut > :checkIn)")
    List<RoomEntity> findAvailableRooms(@Param("roomTypeId") int roomTypeId, @Param("branchId") int branchId, @Param("checkIn") LocalDateTime checkIn, @Param("checkOut") LocalDateTime checkOut);


    List<RoomEntity> findAllByRoomTypeIdAndBranchIdAndBookingsCheckInAfterAndBookingsCheckOutBeforeAndIsDeletedFalse(
            int roomTypeId,
            int branchId,
            LocalDateTime checkIn,
            LocalDateTime checkOut
    );
>>>>>>> 2c31b00 (update commit)
}
