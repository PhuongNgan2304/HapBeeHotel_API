package com.hotel.hotelapi.controller;

import java.io.IOException;
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
}



