package org.example.schedulemicroservice.providers;

import org.example.schedulemicroservice.entities.Lesson;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.*;
import org.springframework.stereotype.Component;

@Component
public class ScheduleConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory){
        return new Constraint[]{
                teacherConflict(factory),
                classroomConflict(factory),
                classGroupConflict(factory),
                subjectFrequencyConstraint(factory),
                oneTeacherPerSubjectConstraint(factory)
        };
    }

    //a teacher to teach in only 1 class a time
    private Constraint teacherConflict(ConstraintFactory factory){
        return factory.forEach(Lesson.class)
                .join(Lesson.class,
                        Joiners.equal(Lesson::getTimeslot),
                        Joiners.equal(Lesson::getTeacher),
                        Joiners.lessThan(Lesson::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Teacher conflict");
    }

    //a classroom to be used by only 1 class at a time
    private Constraint classroomConflict(ConstraintFactory factory) {
        return factory.forEach(Lesson.class)
                .join(Lesson.class,
                        Joiners.equal(Lesson::getTimeslot),
                        Joiners.equal(Lesson::getClassroom),
                        Joiners.lessThan(Lesson::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Classroom conflict");
    }

    //a class group to have only 1 class at a time
    private Constraint classGroupConflict(ConstraintFactory factory) {
        return factory.forEach(Lesson.class)
                .join(Lesson.class,
                        Joiners.equal(Lesson::getTimeslot),
                        Joiners.equal(Lesson::getClassGroup),
                        Joiners.lessThan(Lesson::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("ClassGroup conflict");
    }

    //a subject to be taught the correct number of times per week
    private Constraint subjectFrequencyConstraint(ConstraintFactory factory){
        return factory.forEach(Lesson.class)
                .groupBy(
                        Lesson::getClassGroup,
                        Lesson::getSubject,
                        ConstraintCollectors.count()
                )
                .filter((classGroup, subject, count) -> count != subject.getFrequencyPerWeek())
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Subject frequency mismatch");
    }

    //a subject to be taught by only 1 teacher in a class group
    private Constraint oneTeacherPerSubjectConstraint(ConstraintFactory factory){
        return factory.forEach(Lesson.class)
                .join(Lesson.class,
                        Joiners.equal(Lesson::getClassGroup),
                        Joiners.equal(Lesson::getSubject),
                        Joiners.lessThan(Lesson::getId))
                .filter(((lesson1, lesson2) -> !lesson1.getTeacher().equals(lesson2.getTeacher())))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Multiple teachers for the same subject in same class");
    }
}

