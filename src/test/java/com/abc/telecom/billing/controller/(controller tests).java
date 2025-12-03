package com.abc.telecom.billing.controller;

import com.abc.telecom.billing.dto.auth.LoginRequest;
import com.abc.telecom.billing.dto.auth.JwtResponse;
import com.abc.telecom.billing.entity.User;
import com.abc.telecom.billing.service.AuthService;
import com.abc.telecom.billing.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");
        JwtResponse jwtResponse = new JwtResponse("dummyToken");

        when(authService.authenticateUser(loginRequest)).thenReturn(jwtResponse);

        ResponseEntity<JwtResponse> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jwtResponse, response.getBody());
        verify(authService, times(1)).authenticateUser(loginRequest);
    }

    @Test
    public void testRegister() {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("newPassword");

        when(authService.registerUser(user)).thenReturn(user);

        ResponseEntity<User> response = authController.register(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(authService, times(1)).registerUser(user);
    }
}