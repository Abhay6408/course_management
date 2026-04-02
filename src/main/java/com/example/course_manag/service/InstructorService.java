package com.example.course_manag.service;

import com.example.course_manag.dao.dto.CreateInstructorRequest;
import com.example.course_manag.dao.dto.InstructorResponse;
import com.example.course_manag.dao.dto.UpdateInstructorRequest;
import com.example.course_manag.dao.entity.Instructor;
import com.example.course_manag.dao.entity.User;
import com.example.course_manag.exception.GlobalExceptionHandler;
import com.example.course_manag.repository.InstructorRepository;
import com.example.course_manag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.course_manag.Enums.Role.ROLE_INSTRUCTOR;

@Service
public class InstructorService {


    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private UserRepository userRepository;

    public List<InstructorResponse> getAllInstructors() {
        return instructorRepository.findAll()
                .stream()
                .map(instructor -> {
                    InstructorResponse response = new InstructorResponse();
                    response.setId(instructor.getId());
                    response.setBio(instructor.getBio());
                    response.setExpertise(instructor.getExpertise());
                    if (instructor.getUser() != null) {
                        response.setUserId(instructor.getUser().getId());
                        response.setFullName(instructor.getUser().getFullName());
                        response.setEmail(instructor.getUser().getEmail());
                    }
                    if (instructor.getCourses() != null) {
                        response.setCourseCount(instructor.getCourses().size());
                    }
                    return response;
                })
                .toList();
    }

    public InstructorResponse getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found: " + id));

        InstructorResponse response = new InstructorResponse();
        response.setId(instructor.getId());
        response.setBio(instructor.getBio());
        response.setExpertise(instructor.getExpertise());
        if (instructor.getUser() != null) {
            response.setUserId(instructor.getUser().getId());
            response.setFullName(instructor.getUser().getFullName());
            response.setEmail(instructor.getUser().getEmail());
        }
        if (instructor.getCourses() != null) {
            response.setCourseCount(instructor.getCourses().size());
        }
        return response;
    }

    public InstructorResponse createInstructor(CreateInstructorRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("User not found: " + request.getUserId()));

        if (user.getRole().equals( ROLE_INSTRUCTOR))
            throw new GlobalExceptionHandler.BadRequestException(
                    "User must be promoted to INSTRUCTOR first via PATCH /api/users/" + user.getId() + "/promote");

        if (instructorRepository.existsByUserId(user.getId()))
            throw new GlobalExceptionHandler.ConflictException("Instructor profile already exists for this user");

        Instructor instructor = Instructor.builder()
                .user(user).bio(request.getBio()).expertise(request.getExpertise()).build();

        instructorRepository.save(instructor);
        InstructorResponse response = new InstructorResponse();
        response.setId(instructor.getId());
        response.setBio(instructor.getBio());
        response.setExpertise(instructor.getExpertise());
            response.setUserId(instructor.getUser().getId());
            response.setFullName(instructor.getUser().getFullName());
            response.setEmail(instructor.getUser().getEmail());

        if (instructor.getCourses() != null) {
            response.setCourseCount(instructor.getCourses().size());
        }
        return response;

    }

    public InstructorResponse updateInstructor(Long id, UpdateInstructorRequest request) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found: " + id));

        if (request.getBio() != null)       instructor.setBio(request.getBio());
        if (request.getExpertise() != null) instructor.setExpertise(request.getExpertise());

        Instructor saved = instructorRepository.save(instructor);

        InstructorResponse response = new InstructorResponse();
        response.setId(saved.getId());
        response.setBio(saved.getBio());
        response.setExpertise(saved.getExpertise());
        if (saved.getUser() != null) {
            response.setUserId(saved.getUser().getId());
            response.setFullName(saved.getUser().getFullName());
            response.setEmail(saved.getUser().getEmail());
        }
        if (saved.getCourses() != null) {
            response.setCourseCount(saved.getCourses().size());
        }
        return response;
    }

    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found: " + id));
        instructorRepository.delete(instructor);
    }





}
