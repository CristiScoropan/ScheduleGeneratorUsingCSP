package org.example.schedulemicroservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.schedulemicroservice.entities.*;
import org.example.schedulemicroservice.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonGeneratorService {
    private final SubjectRepository subjectRepository;
    private final ClassGroupRepository classGroupRepository;
    private final UserReferenceRepository userReferenceRepository;
    private final LessonRepository lessonRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void generateLessonsForSchedule(Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + scheduleId));

        List<Subject> subjects = subjectRepository.findAll();
        List<ClassGroup> classGroups = classGroupRepository.findAll();
        List<Lesson> lessons = new ArrayList<>();

        for (ClassGroup classGroup : classGroups) {
            for (Subject subject : subjects) {
                Teacher teacher = subject.getTeacher();
                UserReference user = userReferenceRepository.findById(teacher.getId())
                        .orElseThrow(() -> new RuntimeException("UserReference not found for teacher ID: " + teacher.getId()));

                for (int i = 0; i < subject.getFrequencyPerWeek(); i++) {
                    lessons.add(Lesson.builder()
                            .subject(subject)
                            .teacher(teacher)
                            .classGroup(classGroup)
                            .userReference(user)
                            .build());
                }
            }
        }

        lessonRepository.saveAll(lessons);
        schedule.setLessonList(lessons);
        scheduleRepository.save(schedule);
    }
}
