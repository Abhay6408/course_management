package com.example.course_manag.service;

import com.example.course_manag.dao.dto.EnrollRequest;
import com.example.course_manag.dao.dto.EnrollmentResponse;
import com.example.course_manag.dao.entity.Course;
import com.example.course_manag.dao.entity.Enrollment;
import com.example.course_manag.dao.entity.User;
import com.example.course_manag.repository.CourseRepository;
import com.example.course_manag.repository.EnrollmentRepository;
import com.example.course_manag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;


    public EnrollmentResponse enroll(EnrollRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User student = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found: " + request.getCourseId()));



        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId()))
            throw new RuntimeException("Already enrolled in this course");

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();

        enrollmentRepository.save(enrollment);
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());
            response.setStudentId(enrollment.getStudent().getId());
            response.setStudentName(enrollment.getStudent().getFullName());

            response.setCourseId(enrollment.getCourse().getId());
            response.setCourseTitle(enrollment.getCourse().getTitle());

        return response;
    }

    public List<EnrollmentResponse> getMyEnrollments() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User student = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return enrollmentRepository.findByStudentId(student.getId())
                .stream()
                .map(enrollment -> {
                    EnrollmentResponse response = new EnrollmentResponse();
                    response.setId(enrollment.getId());
                        response.setStudentId(enrollment.getStudent().getId());
                        response.setStudentName(enrollment.getStudent().getFullName());

                        response.setCourseId(enrollment.getCourse().getId());
                        response.setCourseTitle(enrollment.getCourse().getTitle());

                    return response;
                })
                .toList();
    }


    public List<EnrollmentResponse> getEnrollmentsByCourse(Long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(enrollment -> {
                    EnrollmentResponse response = new EnrollmentResponse();
                    response.setId(enrollment.getId());
                        response.setStudentId(enrollment.getStudent().getId());
                        response.setStudentName(enrollment.getStudent().getFullName());

                        response.setCourseId(enrollment.getCourse().getId());
                        response.setCourseTitle(enrollment.getCourse().getTitle());

                    return response;
                })
                .toList();
    }


    public void dropEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found: " + enrollmentId));

        enrollmentRepository.delete(enrollment);


    }



}
