package org.example.schedulemicroservice.services;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.entities.Classroom;
import org.example.schedulemicroservice.entities.Lesson;
import org.example.schedulemicroservice.entities.Schedule;
import org.example.schedulemicroservice.entities.Timeslot;
import org.example.schedulemicroservice.repositories.ClassroomRepository;
import org.example.schedulemicroservice.repositories.LessonRepository;
import org.example.schedulemicroservice.repositories.TimeslotRepository;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class SchedulingSolverService {
    private final SolverManager<Schedule, Long> solverManager;
    private final LessonRepository lessonRepository;
    private final TimeslotRepository timeslotRepository;
    private final ClassroomRepository classroomRepository;

    public Schedule solve(Long problemId) throws ExecutionException, InterruptedException {
        return solverManager.solve(problemId, this::buildProblem, null, null).getFinalBestSolution();
    }

    public Schedule buildProblem(Long problemId){
        List<Lesson> lessons = lessonRepository.findAll();
        List<Timeslot> timeslots = timeslotRepository.findAll();
        List<Classroom> classrooms = classroomRepository.findAll();

        Schedule problem = new Schedule();
        problem.setLessonList(lessons);
        problem.setTimeslotList(timeslots);
        problem.setClassroomList(classrooms);

        return problem;
    }
}
