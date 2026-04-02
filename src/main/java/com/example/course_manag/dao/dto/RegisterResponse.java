package com.example.course_manag.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterResponse {

    private Long id;
    private String email;
    private String name;
    private String role;
    private String status;

}
