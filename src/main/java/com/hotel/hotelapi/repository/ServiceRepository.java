package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {
//    List<ServiceEntity> findServiceEntitiesByIsDeleteIsTrue();
//    List<ServiceEntity> findServiceEntitiesByIsDeleteIsFalse();

    List<ServiceEntity> findAllByIsDeletedFalse();
    List<ServiceEntity> findAllByIsDeletedTrue();
    Optional<ServiceEntity> findByIdAndIsDeletedFalse(int id);

    List<ServiceEntity> findByNameAndIsDeletedFalse(String name);
    Page<ServiceEntity> findAllByIsDeletedFalse(Pageable pageable);
    Page<ServiceEntity> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);

}