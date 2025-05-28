package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.entities.Schedule;
import org.example.schedulemicroservice.services.SchedulingSolverService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final SchedulingSolverService schedulingSolverService;

    @PostMapping("/solve")
    public Schedule solve(@RequestParam Long problemId) throws ExecutionException, InterruptedException {
        return schedulingSolverService.solve(problemId);
    }
}
