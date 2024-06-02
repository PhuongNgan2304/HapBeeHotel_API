package com.hotel.hotelapi.repository;

import com.hotel.hotelapi.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

  @Query(value = """
      select t from TokenEntity t inner join UserEntity u\s
      on t.userEntity.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<TokenEntity> findAllValidTokenByUser(Integer id);

  Optional<TokenEntity> findByToken(String token);
}
