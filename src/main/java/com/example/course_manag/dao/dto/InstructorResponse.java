package com.example.course_manag.dao.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstructorResponse{

    private Long id;
    private Long userId;
    private String fullName;
    private String email;
    private String bio;
    private String expertise;
    private int courseCount;
}
