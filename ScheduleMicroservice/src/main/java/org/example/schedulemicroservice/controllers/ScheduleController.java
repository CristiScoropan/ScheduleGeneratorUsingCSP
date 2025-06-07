package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.LessonDTO;
import org.example.schedulemicroservice.entities.Schedule;
import org.example.schedulemicroservice.mappers.LessonMapper;
import org.example.schedulemicroservice.services.SchedulingSolverService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final SchedulingSolverService schedulingSolverService;
    private final LessonMapper lessonMapper;

    @PostMapping("/solve")
    public List<LessonDTO> solve(@RequestParam Long problemId) throws ExecutionException, InterruptedException {
        Schedule schedule = schedulingSolverService.solve(problemId);
        return schedule.getLessonList().stream()
                .map(lessonMapper::toDTO)
                .sorted(Comparator
                        .comparing(LessonDTO::getClassGroup)
                        .thenComparing(dto -> extractDayOrder(dto.getTimeslot()))
                        .thenComparing(dto -> extractStartTime(dto.getTimeslot())))
                .toList();
    }

    private int extractDayOrder(String timeslot) {
        if (timeslot == null) return 7;
        String day = timeslot.split(" ")[0].toLowerCase();
        return switch (day) {
            case "monday" -> 1;
            case "tuesday" -> 2;
            case "wednesday" -> 3;
            case "thursday" -> 4;
            case "friday" -> 5;
            default -> 6;
        };
    }

    private String extractStartTime(String timeslot) {
        if (timeslot == null || !timeslot.contains(" ")) return "00:00";
        String[] parts = timeslot.split(" ");
        if (parts.length < 2) return "00:00";
        return parts[1].split("-")[0]; // e.g., "08:00"
    }


}
