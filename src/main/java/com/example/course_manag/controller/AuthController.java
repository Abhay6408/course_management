package com.example.course_manag.controller;

import com.example.course_manag.dao.dto.RegisterRequest;
import com.example.course_manag.dao.dto.RegisterResponse;
import com.example.course_manag.dao.dto.currentUserResponse;
import com.example.course_manag.dao.entity.User;
import com.example.course_manag.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponse(user.getId(), user.getEmail(),
                        user.getFullName(), user.getRole().toString(), "Registration successful"));
    }

    /**
     * Protected endpoint — returns the currently authenticated user's info.
     * Client must send: Authorization: Basic base64(email:password)
     */
    @GetMapping("/currentUser")
    public ResponseEntity<currentUserResponse> me(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(new currentUserResponse(userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority()));
    }
}
