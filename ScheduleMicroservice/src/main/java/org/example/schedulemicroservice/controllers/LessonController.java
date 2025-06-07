package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.LessonDTO;
import org.example.schedulemicroservice.services.LessonGeneratorService;
import org.example.schedulemicroservice.services.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    private final LessonService lessonService;
    private final LessonGeneratorService lessonGeneratorService;

    @GetMapping
    public List<LessonDTO> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PostMapping
    public LessonDTO create(@RequestParam String subject,
                            @RequestParam String teacher,
                            @RequestParam String classGroup,
                            @RequestParam String timeslotDay,
                            @RequestParam String timeslotTime,
                            @RequestParam String classroom,
                            @RequestParam Long userId) {
        return lessonService.create(subject, teacher, classGroup, timeslotDay, timeslotTime, classroom, userId);
    }

    @PutMapping("/{id}")
    public LessonDTO update(@PathVariable Long id,
                            @RequestParam String subject,
                            @RequestParam String teacher,
                            @RequestParam String classGroup,
                            @RequestParam String timeslotDay,
                            @RequestParam String timeslotTime,
                            @RequestParam String classroom,
                            @RequestParam Long userId) {
        return lessonService.update(id, subject, teacher, classGroup, timeslotDay, timeslotTime, classroom, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        lessonService.delete(id);
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateLessons(@RequestParam Long scheduleId) {
        lessonGeneratorService.generateLessonsForSchedule(scheduleId);
        return ResponseEntity.ok("Lessons generated successfully for schedule ID: " + scheduleId);
    }
}
