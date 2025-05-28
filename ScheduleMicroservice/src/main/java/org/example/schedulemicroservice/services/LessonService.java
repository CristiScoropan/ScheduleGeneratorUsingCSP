package org.example.schedulemicroservice.services;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.LessonDTO;
import org.example.schedulemicroservice.entities.Lesson;
import org.example.schedulemicroservice.mappers.LessonMapper;
import org.example.schedulemicroservice.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final ClassGroupService classGroupService;
    private final ClassroomService classroomService;
    private final TimeslotService timeslotService;

    public List<LessonDTO> getAllLessons(){
        return lessonRepository.findAll()
                .stream()
                .map(lessonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LessonDTO create(String subjectName, String teacherName, String classGroupName,
                                  String classroomName, String dayOfWeek, String time) {
        Lesson lesson = new Lesson();

        lesson.setSubject(subjectService.getSubjectByName(subjectName));
        lesson.setTeacher(teacherService.getTeacherByName(teacherName));
        lesson.setClassGroup(classGroupService.getClassGroupByName(classGroupName));
        lesson.setClassroom(classroomService.getClassroomByName(classroomName));
        lesson.setTimeslot(timeslotService.getTimeslotByDayAndTime(dayOfWeek, time));

        return lessonMapper.toDTO(lessonRepository.save(lesson));
    }

    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }

    public LessonDTO update(Long id, String subjectName, String teacherName, String classGroupName,
                                  String classroomName, String dayOfWeek, String time) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        lesson.setSubject(subjectService.getSubjectByName(subjectName));
        lesson.setTeacher(teacherService.getTeacherByName(teacherName));
        lesson.setClassGroup(classGroupService.getClassGroupByName(classGroupName));
        lesson.setClassroom(classroomService.getClassroomByName(classroomName));
        lesson.setTimeslot(timeslotService.getTimeslotByDayAndTime(dayOfWeek, time));

        return lessonMapper.toDTO(lessonRepository.save(lesson));
    }
}
