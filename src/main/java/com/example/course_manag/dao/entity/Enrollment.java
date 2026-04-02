package com.example.course_manag.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private EnrollmentStatus status;

//    @Column(nullable = false, updatable = false)
//    private LocalDateTime enrolledAt;
//
//    private LocalDateTime completedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        enrolledAt = LocalDateTime.now();
//        if (status == null) status = EnrollmentStatus.ACTIVE;
//    }

//    public enum EnrollmentStatus {
//        ACTIVE, COMPLETED, DROPPED
//    }
}
