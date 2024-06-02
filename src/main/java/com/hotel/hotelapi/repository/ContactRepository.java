package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity,Integer> {
    List<ContactEntity> findAllByStatusEquals(int status);
}
