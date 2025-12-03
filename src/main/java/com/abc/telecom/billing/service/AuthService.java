package com.abc.telecom.billing.service;

import com.abc.telecom.billing.dto.auth.JwtResponse;
import com.abc.telecom.billing.dto.auth.LoginRequest;
import com.abc.telecom.billing.dto.auth.UserDto;
import com.abc.telecom.billing.entity.User;
import com.abc.telecom.billing.entity.Role;
import com.abc.telecom.billing.repository.RoleRepository;
import com.abc.telecom.billing.repository.UserRepository;
import com.abc.telecom.billing.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.abc.telecom.billing.exception.ApiException;
import org.springframework.http.HttpStatus;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);

        return new JwtResponse(token);
    }

    public User registerUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new ApiException("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ApiException("Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // assign CUSTOMER role by default
        Role customerRole = roleRepository.findByName(Role.RoleName.CUSTOMER);
        if (customerRole == null) {
            customerRole = roleRepository.save(new Role(Role.RoleName.CUSTOMER));
        }
        java.util.Set<Role> roles = new java.util.HashSet<>();
        roles.add(customerRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String[] roles = user.getRoles().stream().map(r -> r.getName().name()).toArray(String[]::new);
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(roles)
            .build();
    }
}