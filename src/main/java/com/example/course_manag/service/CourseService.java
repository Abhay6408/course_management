package com.example.course_manag.service;

import com.example.course_manag.dao.dto.CourseResponse;
import com.example.course_manag.dao.dto.CreateCourseRequest;
import com.example.course_manag.dao.dto.UpdateCourseRequest;
import com.example.course_manag.dao.entity.Course;
import com.example.course_manag.dao.entity.Instructor;
import com.example.course_manag.exception.GlobalExceptionHandler;
import com.example.course_manag.repository.CourseRepository;
import com.example.course_manag.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> {
                    CourseResponse response = new CourseResponse();
                    response.setId(course.getId());
                    response.setTitle(course.getTitle());
                    response.setDescription(course.getDescription());

                        response.setInstructorId(course.getInstructor().getId());
                        response.setInstructorName(course.getInstructor().getUser().getFullName());

                    if (course.getChapters() != null) {
                        response.setLessonCount(course.getChapters().size());
                    }
                    return response;
                })
                .toList();
    }

    public CourseResponse getCourseById(Long id) {

        return courseRepository.findById(id).map(course -> {
                    CourseResponse response = new CourseResponse();
                    response.setId(course.getId());
                    response.setTitle(course.getTitle());
                    response.setDescription(course.getDescription());

                        response.setInstructorId(course.getInstructor().getId());
                        response.setInstructorName(course.getInstructor().getUser().getFullName());

                    if (course.getChapters() != null) {
                        response.setLessonCount(course.getChapters().size());
                    }
                    return response;
                }).orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    public List<CourseResponse> getCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId).stream().map(course -> {
                    CourseResponse response = new CourseResponse();
                    response.setId(course.getId());
                    response.setTitle(course.getTitle());
                    response.setDescription(course.getDescription());

                        response.setInstructorId(course.getInstructor().getId());
                        response.setInstructorName(course.getInstructor().getUser().getFullName());

                    if (course.getChapters() != null) {
                        response.setLessonCount(course.getChapters().size());
                    }
                    return response;
                })
                .toList();
    }


    public CourseResponse createCourse(CreateCourseRequest request) {
        Course course = Course.builder()
                .title(request.getTitle()).description(request.getDescription()).build();

        if (request.getInstructorId() != null) {
            Instructor instructor = instructorRepository.findById(request.getInstructorId())
                    .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Instructor not found: " + request.getInstructorId()));
            course.setInstructor(instructor);
        }
        Course savedCourse=(courseRepository.save(course));

        CourseResponse response = new CourseResponse();
        response.setId(savedCourse.getId());
        response.setTitle(savedCourse.getTitle());
        response.setDescription(savedCourse.getDescription());

            response.setInstructorId(savedCourse.getInstructor().getId());
            response.setInstructorName(savedCourse.getInstructor().getUser().getFullName());


        return response;
    }

    public CourseResponse updateCourse(Long id, UpdateCourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                        "Course not found: " + id));

        if (request.getTitle() != null) {
            course.setTitle(request.getTitle());
        }
        if (request.getDescription() != null){
            course.setDescription(request.getDescription());
        }

        Course savedCourse=(courseRepository.save(course));

        CourseResponse response = new CourseResponse();
        response.setId(savedCourse.getId());
        response.setTitle(savedCourse.getTitle());
        response.setDescription(savedCourse.getDescription());

            response.setInstructorId(savedCourse.getInstructor().getId());
            response.setInstructorName(savedCourse.getInstructor().getUser().getFullName());


        return response;

    }

    public void deleteCourse(Long id) {
        Course course=courseRepository.findById(id).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                "Course not found: " + id));
        courseRepository.delete(course);
    }

}
