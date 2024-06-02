package com.hotel.hotelapi.entity;

import com.hotel.hotelapi.user.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String fullname;
  private String avatar_url;
  private String cover_url;
  private String email;
  private String password;
  private LocalDate birthday;
  private String address;
  private String phone;
  private String code;
  private LocalDateTime createDay;
  private boolean isActive;


  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "userEntity")
  private List<TokenEntity> tokens;


  @OneToMany(mappedBy = "user")
  private List<BookingEntity> user_bookings;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<CartRoomEntity> carts;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActive;
  }
}
