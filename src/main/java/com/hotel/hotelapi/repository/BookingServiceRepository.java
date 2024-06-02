package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.BookingServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingServiceRepository extends JpaRepository<BookingServiceEntity,Integer> {

}
