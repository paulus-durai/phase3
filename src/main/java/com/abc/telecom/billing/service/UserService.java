package com.abc.telecom.billing.service;

import com.abc.telecom.billing.entity.User;
import com.abc.telecom.billing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.telecom.billing.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAllWithRoles();
        } catch (java.lang.AbstractMethodError | java.lang.UnsupportedOperationException ex) {
            // Fallback if JPA provider doesn't support EntityGraph on custom method
            return userRepository.findAll();
        }
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ApiException("User not found with id " + id, HttpStatus.NOT_FOUND));
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRoles(userDetails.getRoles());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}