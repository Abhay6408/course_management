package com.example.course_manag.dao.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class currentUserResponse {
    private  String email;
    private  String role;
}
