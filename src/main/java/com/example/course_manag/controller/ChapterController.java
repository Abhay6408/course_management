package com.example.course_manag.controller;

import com.example.course_manag.dao.dto.ChapterResponse;
import com.example.course_manag.dao.dto.CreateChapterRequest;
import com.example.course_manag.dao.dto.UpdateChapterRequest;
import com.example.course_manag.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chapter")
public class ChapterController {



    @Autowired
    private ChapterService chapterService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ChapterResponse>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(chapterService.getChaptersByCourse(courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapterResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(chapterService.getChapterById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ChapterResponse> create( @RequestBody CreateChapterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chapterService.createChapter(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ChapterResponse> update(
            @PathVariable Long id, @RequestBody UpdateChapterRequest request) {
        return ResponseEntity.ok(chapterService.updateChapter(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }

}
