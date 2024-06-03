package com.hotel.hotelapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.hotelapi.config.JwtService;
import com.hotel.hotelapi.entity.TokenEntity;
import com.hotel.hotelapi.entity.UserEntity;
import com.hotel.hotelapi.model.*;
import com.hotel.hotelapi.repository.TokenRepository;
import com.hotel.hotelapi.repository.UserRepository;
import com.hotel.hotelapi.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private IEmailService emailService = new EmailServiceImpl();

  public RegisterResponse register(RegisterRequest request) {
//    Kiểm tra email người dùng có tồn tại hay không?
    if(repository.existsUserEntityByEmail(request.getEmail()))
      throw new RuntimeException("User with email " + request.getEmail() + " already exists.");

//    Gửi code
    String code = this.getRandom();
//    account.setCode(code);
    String body = "Mã xác nhận HapBee Hotel Service của bạn là: "+code+" ! Nếu bạn không đăng ký" +
            "HapBee Hotel Service thì hãy bỏ qua email này!";
    emailService.sendEmail("HapBee Hotel Team",request.getEmail(),"Confirm email",body);
//    account.setCode(getRandom());

    var user = UserEntity.builder()
            .fullname(request.getFullname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .address(request.getAddress())
            .phone(request.getPhone())
            .birthday(request.getBirthday())
            .createDay(LocalDateTime.now())
            .avatar_url(null)
            .cover_url(null)
            .code(code)
            .isActive(false)
        .build();
    var savedUser = repository.save(user);

//    RegisterResponse registerResponse = modelMapper.map(user, RegisterResponse.class);
    return RegisterResponse.builder()
            .fullname(savedUser.getFullname())
            .email(savedUser.getEmail())
            .build();
//    var jwtToken = jwtService.generateToken(user);
//    var refreshToken = jwtService.generateRefreshToken(user);
//    saveUserToken(savedUser, jwtToken);
//    return AuthenticationResponse.builder()
//        .accessToken(jwtToken)
//            .refreshToken(refreshToken)
//        .build();
  }
  public AuthenticationResponse confirmRegister(ConfirmRequest confirmRequest) throws Exception{
    if ("ĐÃ XÁC THỰC".equals(confirmRequest.getCode())) {
      throw new Exception("Lỗi bảo mật!");
    }
//    UserEntity user = repository.findUserByEmail(email);
    var user = repository.findByEmailAndCode(confirmRequest.getEmail(),confirmRequest.getCode()).orElseThrow();

    user.setActive(true);
    user.setCode("ĐÃ XÁC THỰC");
    var savedUser = repository.save(user);

    var jwtToken = jwtService.generateToken(savedUser);
    var refreshToken = jwtService.generateRefreshToken(savedUser);
    saveUserToken(savedUser, jwtToken);

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(UserEntity userEntity, String jwtToken) {
    var token = TokenEntity.builder()
        .userEntity(userEntity)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserEntity userEntity) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(userEntity.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
  public String getRandom() {
    Random rnd = new Random();
    int number = rnd.nextInt(999999);
    return String.format("%06d", number);
  }
}
