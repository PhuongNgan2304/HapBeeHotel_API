package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

//  UserEntity findUserByEmail(String email);
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findByEmailAndCode(String email,String code);
  boolean existsUserEntityByEmail(String email);
  List<UserEntity> findByFullnameIgnoreCaseContaining(String fullname);
}
