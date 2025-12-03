package com.abc.telecom.billing.controller;

import com.abc.telecom.billing.dto.UserDto;
import com.abc.telecom.billing.entity.User;
import com.abc.telecom.billing.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<java.util.List<UserDto>> getAllUsers() {
        java.util.List<User> users = userService.getAllUsers();
        java.util.List<UserDto> dtos = users.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        java.util.Optional<User> opt = userService.getUserById(id);
        if (!opt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelMapper.map(opt.get(), UserDto.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(modelMapper.map(createdUser, UserDto.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(modelMapper.map(updatedUser, UserDto.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}