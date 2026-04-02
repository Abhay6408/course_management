package com.example.course_manag.repository;

import com.example.course_manag.dao.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByInstructorId(Long instructorId);
    Optional<Course> findById(Long courseId);
}
