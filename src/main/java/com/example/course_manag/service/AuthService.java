package com.example.course_manag.service;

import com.example.course_manag.dao.dto.LoginRequest;
import com.example.course_manag.dao.dto.RegisterRequest;
import com.example.course_manag.dao.entity.User;
import com.example.course_manag.exception.GlobalExceptionHandler;
import com.example.course_manag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.course_manag.Enums.Role.ROLE_STUDENT;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already in use: " + request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getName())
                .role(ROLE_STUDENT)
                .build();

        return userRepository.save(user);
    }

//    public User getCurrentUser(){
//
//    }

}
