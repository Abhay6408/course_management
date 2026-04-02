package com.example.course_manag.dao.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCourseRequest {
    private  String title;

    private String description;
    private Long instructorId;
}
