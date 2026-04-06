package com.example.course_manag.controller;

import com.example.course_manag.dao.dto.UserResponse;
import com.example.course_manag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /** Promote student → instructor */
    @PatchMapping("/{id}/promote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> promote(@PathVariable Long id) {
        return ResponseEntity.ok(userService.promoteToInstructor(id));
    }

    /** Demote instructor → student */
    @PatchMapping("/{id}/demote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> demote(@PathVariable Long id) {
        return ResponseEntity.ok(userService.demoteToStudent(id));
    }

}
