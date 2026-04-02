package com.example.course_manag.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChapterResponse {

    private Long id;
    private String title;
    private String content;
    private String videoUrl;
    private Integer orderIndex;
    private Integer durationMinutes;
    private Long courseId;
    private String courseTitle;

}
