package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.BranchEntity;
import com.hotel.hotelapi.entity.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, Integer> {
    List<BranchEntity> findAllByIsDeletedFalse();
    List<BranchEntity> findAllByIsDeletedTrue();
    Optional<BranchEntity> findByIdAndIsDeletedFalse(int id);
    Optional<BranchEntity> findByIdAndIsDeletedTrue(int id);
    //List<BranchEntity> findByNameAndIsDeletedFalse (String name);
    Page<BranchEntity> findAllByIsDeletedFalse(Pageable pageable);
    Page<BranchEntity> findByLocationContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);
    //    @Query("SELECT b FROM BranchEntity b JOIN b.rooms r WHERE r.roomType.id = :roomTypeId AND r.isDeleted = FALSE ")
//    List<BranchEntity> findBranchesByRoomTypeIdAndIsDeletedIsFalse(@Param("roomTypeId") int roomTypeId);
    List<BranchEntity> findAllByAndRoomsRoomTypeIdAndIsDeletedIsFalse(int id);
}
