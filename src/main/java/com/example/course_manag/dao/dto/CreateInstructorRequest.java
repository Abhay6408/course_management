package com.example.course_manag.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateInstructorRequest {

        private Long userId;

        private String bio;

        private String expertise;
    }

