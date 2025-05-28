package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.LessonDTO;
import org.example.schedulemicroservice.services.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    private final LessonService lessonService;

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
                            @RequestParam String classroom) {
        return lessonService.create(subject, teacher, classGroup, timeslotDay, timeslotTime, classroom);
    }

    @PutMapping("/{id}")
    public LessonDTO update(@PathVariable Long id,
                            @RequestParam String subject,
                            @RequestParam String teacher,
                            @RequestParam String classGroup,
                            @RequestParam String timeslotDay,
                            @RequestParam String timeslotTime,
                            @RequestParam String classroom) {
        return lessonService.update(id, subject, teacher, classGroup, timeslotDay, timeslotTime, classroom);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        lessonService.delete(id);
    }
}
