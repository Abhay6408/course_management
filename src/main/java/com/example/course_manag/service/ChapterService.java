package com.example.course_manag.service;

import com.example.course_manag.dao.dto.ChapterResponse;
import com.example.course_manag.dao.dto.CreateChapterRequest;
import com.example.course_manag.dao.dto.UpdateChapterRequest;
import com.example.course_manag.dao.entity.Chapter;
import com.example.course_manag.dao.entity.Course;
import com.example.course_manag.repository.ChapterRepository;
import com.example.course_manag.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {


    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private CourseRepository courseRepository;

    public List<ChapterResponse> getChaptersByCourse(Long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        return chapterRepository.findByCourseIdOrderByOrderIndexAsc(courseId)
                .stream()
                .map(chapter -> {
                    ChapterResponse response = new ChapterResponse();
                    response.setId(chapter.getId());
                    response.setTitle(chapter.getTitle());
                    response.setContent(chapter.getContent());
                    response.setVideoUrl(chapter.getVideoUrl());
                    response.setOrderIndex(chapter.getOrderIndex());
                    response.setDurationMinutes(chapter.getDurationMinutes());

                        response.setCourseId(chapter.getCourse().getId());
                        response.setCourseTitle(chapter.getCourse().getTitle());

                    return response;
                })
                .toList();
    }

    public ChapterResponse getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found: " + id));

        ChapterResponse response = new ChapterResponse();
        response.setId(chapter.getId());
        response.setTitle(chapter.getTitle());
        response.setContent(chapter.getContent());
        response.setVideoUrl(chapter.getVideoUrl());
        response.setOrderIndex(chapter.getOrderIndex());
        response.setDurationMinutes(chapter.getDurationMinutes());
        if (chapter.getCourse() != null) {
            response.setCourseId(chapter.getCourse().getId());
            response.setCourseTitle(chapter.getCourse().getTitle());
        }
        return response;
    }


    public ChapterResponse createChapter(CreateChapterRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found: " + request.getCourseId()));

        Chapter chapter = Chapter.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .videoUrl(request.getVideoUrl())
                .orderIndex(request.getOrderIndex())
                .durationMinutes(request.getDurationMinutes())
                .course(course)
                .build();

        chapterRepository.save(chapter);

        ChapterResponse response = new ChapterResponse();
        response.setId(chapter.getId());
        response.setTitle(chapter.getTitle());
        response.setContent(chapter.getContent());
        response.setVideoUrl(chapter.getVideoUrl());
        response.setOrderIndex(chapter.getOrderIndex());
        response.setDurationMinutes(chapter.getDurationMinutes());

            response.setCourseId(chapter.getCourse().getId());
            response.setCourseTitle(chapter.getCourse().getTitle());

        return response;
    }


    public ChapterResponse updateChapter(Long id, UpdateChapterRequest request) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found: " + id));

        if (request.getTitle() != null)           chapter.setTitle(request.getTitle());
        if (request.getContent() != null)         chapter.setContent(request.getContent());
        if (request.getVideoUrl() != null)        chapter.setVideoUrl(request.getVideoUrl());
        if (request.getOrderIndex() != null)      chapter.setOrderIndex(request.getOrderIndex());
        if (request.getDurationMinutes() != null) chapter.setDurationMinutes(request.getDurationMinutes());

        chapterRepository.save(chapter);

        ChapterResponse response = new ChapterResponse();
        response.setId(chapter.getId());
        response.setTitle(chapter.getTitle());
        response.setContent(chapter.getContent());
        response.setVideoUrl(chapter.getVideoUrl());
        response.setOrderIndex(chapter.getOrderIndex());
        response.setDurationMinutes(chapter.getDurationMinutes());
        if (chapter.getCourse() != null) {
            response.setCourseId(chapter.getCourse().getId());
            response.setCourseTitle(chapter.getCourse().getTitle());
        }
        return response;
    }


    public void deleteChapter(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found: " + id));
        chapterRepository.delete(chapter);
    }



}
