package com.abc.telecom.billing.controller;

import com.abc.telecom.billing.dto.auth.LoginRequest;
import com.abc.telecom.billing.dto.auth.JwtResponse;
import com.abc.telecom.billing.dto.auth.UserDto;
import com.abc.telecom.billing.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwt = authService.login(loginRequest);
            return ResponseEntity.ok(jwt);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authService.registerUser(userDto));
    }
}