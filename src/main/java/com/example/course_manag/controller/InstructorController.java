package com.example.course_manag.controller;

import com.example.course_manag.dao.dto.CreateInstructorRequest;
import com.example.course_manag.dao.dto.InstructorResponse;
import com.example.course_manag.dao.dto.UpdateInstructorRequest;
import com.example.course_manag.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/instructor")
public class InstructorController {


    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public ResponseEntity<List<InstructorResponse>> getAll() {
        return ResponseEntity.ok(instructorService.getAllInstructors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.getInstructorById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstructorResponse> create(@RequestBody CreateInstructorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.createInstructor(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<InstructorResponse> update(
            @PathVariable Long id, @RequestBody UpdateInstructorRequest request) {
        return ResponseEntity.ok(instructorService.updateInstructor(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }

}
