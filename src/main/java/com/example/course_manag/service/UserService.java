package com.example.course_manag.service;

import com.example.course_manag.dao.dto.UserResponse;
import com.example.course_manag.dao.entity.User;
import com.example.course_manag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.example.course_manag.Enums.Role.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponse response = new UserResponse();
                    response.setId(user.getId());
                    response.setEmail(user.getEmail());
                    response.setFullName(user.getFullName());
                    response.setRole(user.getRole().name());
                    return response;
                })
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setRole(user.getRole().name());
        return response;
    }

    public UserResponse promoteToInstructor(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        if (user.getRole().equals(ROLE_ADMIN)) {
            throw new RuntimeException("Cannot change role of an admin");
        }
        if (user.getRole().equals(ROLE_INSTRUCTOR)) {
            throw new RuntimeException("User is already an instructor");
        }

        user.setRole(ROLE_INSTRUCTOR);
        User saved = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setFullName(saved.getFullName());
        response.setRole(saved.getRole().name());
        return response;
    }

    public UserResponse demoteToStudent(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        if (user.getRole().equals(ROLE_ADMIN)) {
            throw new RuntimeException("Cannot change role of an admin");
        }
        if (user.getRole().equals(ROLE_STUDENT)) {
            throw new RuntimeException("User is already an student");
        }

        user.setRole(ROLE_STUDENT);
        User saved = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setFullName(saved.getFullName());
        response.setRole(saved.getRole().name());
        return response;
    }
}
