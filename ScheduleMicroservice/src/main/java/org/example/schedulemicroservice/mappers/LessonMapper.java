package org.example.schedulemicroservice.mappers;

import org.example.schedulemicroservice.dtos.LessonDTO;
import org.example.schedulemicroservice.dtos.UserReferenceDTO;
import org.example.schedulemicroservice.entities.Lesson;
import org.example.schedulemicroservice.entities.UserReference;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {
    public LessonDTO toDTO(Lesson lesson){
        return new LessonDTO(
                lesson.getSubject() != null ? lesson.getSubject().getName() : null,
                lesson.getTeacher() != null ? lesson.getTeacher().getName() : null,
                lesson.getClassGroup() != null ? lesson.getClassGroup().getName() : null,
                lesson.getTimeslot() != null ? lesson.getTimeslot().getDayOfWeek() + " " + lesson.getTimeslot().getTime() : null,
                lesson.getClassroom() != null ? lesson.getClassroom().getName() : null,
                lesson.getUserReference() != null ? toUserReferenceDTO(lesson.getUserReference()) :null

        );
    }

    private UserReferenceDTO toUserReferenceDTO(UserReference user){
        return new UserReferenceDTO(user.getUserId(), user.getUsername());
    }
}
