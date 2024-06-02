package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    @Query("SELECT b FROM BookingEntity b WHERE b.room.id = :roomId AND " +
            "(b.checkIn <= :checkOut AND b.checkOut >= :checkIn)")
    List<BookingEntity> findConflictingBookings(@Param("roomId") int roomId,
                                                @Param("checkIn") LocalDateTime checkIn,
                                                @Param("checkOut") LocalDateTime checkOut);
    @Query("SELECT b FROM BookingEntity b WHERE b.room.id = :roomId AND b.checkOut > CURRENT_TIMESTAMP AND b.checkIn < CURRENT_TIMESTAMP")
    List<BookingEntity> findCurrentBookingsByRoomId(@Param("roomId") int roomId);

    @Query("SELECT b FROM BookingEntity b WHERE b.room.branch.id = :branchId")
    List<BookingEntity> findBookingsByBranch(@Param("branchId") int branchId);
}
