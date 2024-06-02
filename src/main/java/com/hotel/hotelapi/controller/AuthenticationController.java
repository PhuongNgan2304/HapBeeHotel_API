package com.hotel.hotelapi.controller;

<<<<<<< HEAD
import com.hotel.hotelapi.entity.AccountEntity;
import com.hotel.hotelapi.exception.MyException;
import com.hotel.hotelapi.model.AuthenticationModel;
import com.hotel.hotelapi.service.AccountServiceImpl;
import com.hotel.hotelapi.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    private IAccountService accountService = new AccountServiceImpl();

    @PostMapping("/login")
    public AuthenticationModel login(@RequestParam String username, @RequestParam String password) throws Exception{
        return accountService.login(username, password);
//        return null;
    }
    @GetMapping("register")
    public AuthenticationModel regiser(@RequestBody AccountEntity account) throws Exception {
        return accountService.register(account);
    }

    @PostMapping("checkLogin")
    public boolean checkLogin(@RequestBody AuthenticationModel authenticationModel){
        return accountService.checkLogin(authenticationModel);
    }
=======
import com.hotel.hotelapi.model.*;
import com.hotel.hotelapi.service.AuthenticationService;
import com.hotel.hotelapi.user.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(
      @RequestBody RegisterRequest request
  ) {
    request.setRole(Role.USER);
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/confirm-email")
  public ResponseEntity<AuthenticationResponse> confirm(@RequestBody ConfirmRequest confirmRequest) throws Exception {
    return ResponseEntity.ok(service.confirmRegister(confirmRequest));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


>>>>>>> 2c31b00 (update commit)
}
