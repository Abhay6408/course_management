package com.example.course_manag.controller;



import com.example.course_manag.dao.dto.CourseResponse;
import com.example.course_manag.dao.dto.CreateCourseRequest;
import com.example.course_manag.dao.dto.UpdateCourseRequest;
import com.example.course_manag.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // ─── Public ───────────────────────────────────────────────────────────────

//    @GetMapping
//    public ResponseEntity<Page<CourseDto.Response>> getPublished(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return ResponseEntity.ok(courseService.getPublishedCourses(page, size));
//    }

//    @GetMapping("/search")
//    public ResponseEntity<Page<CourseDto.Response>> search(
//            @RequestParam String keyword,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return ResponseEntity.ok(courseService.searchCourses(keyword, page, size));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<CourseResponse>> getCourseByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(courseService.getCoursesByInstructor(instructorId));
    }

    // ─── Admin only ───────────────────────────────────────────────────────────

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CourseResponse>> getAll(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // ─── Instructor only ──────────────────────────────────────────────────────

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<CourseResponse> create(@RequestBody CreateCourseRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<CourseResponse> update(
            @PathVariable Long id,@RequestBody UpdateCourseRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

//    @PatchMapping("/{id}/publish")
//    @PreAuthorize("hasRole('INSTRUCTOR')")
//    public ResponseEntity<CourseDto.Response> publish(@PathVariable Long id) {
//        return ResponseEntity.ok(courseService.publishCourse(id));
//    }

//    @PatchMapping("/{id}/archive")
//    @PreAuthorize("hasRole('INSTRUCTOR')")
//    public ResponseEntity<CourseDto.Response> archive(@PathVariable Long id) {
//        return ResponseEntity.ok(courseService.archiveCourse(id));
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}

