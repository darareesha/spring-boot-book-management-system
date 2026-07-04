package com.example.bookmanagement_day7.service;

import  com.example.bookmanagement_day7.dto.LoginRequestDto;
import  com.example.bookmanagement_day7.dto.LoginResponseDto;
import  com.example.bookmanagement_day7.dto.RegisterRequestDto;
import  com.example.bookmanagement_day7.entity.Role;
import  com.example.bookmanagement_day7.entity.User;
import  com.example.bookmanagement_day7.exception.ResourceNotFoundException;
import  com.example.bookmanagement_day7.repository.UserRepository;
import  com.example.bookmanagement_day7.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequestDto request) {
        User user = new User();
        user.setUsername(request.getUsername());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        } else {
            user.setRole(Role.USER);
        }

        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        User user = userOptional.orElseThrow(() ->
                new ResourceNotFoundException("User not found with username: " + request.getUsername()));

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new ResourceNotFoundException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new LoginResponseDto(token);
    }
}