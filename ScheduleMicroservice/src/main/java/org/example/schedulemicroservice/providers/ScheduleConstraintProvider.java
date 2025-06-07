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
                oneTeacherPerSubjectConstraint(factory),
                avoidMultipleNonConsecutiveSubjectLessonsPerDay(factory)
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

    private Constraint avoidMultipleNonConsecutiveSubjectLessonsPerDay(ConstraintFactory factory) {
        return factory.forEach(Lesson.class)
                .join(Lesson.class,
                        Joiners.equal(Lesson::getClassGroup),
                        Joiners.equal(Lesson::getSubject),
                        Joiners.filtering((l1, l2) -> {
                            if (l1.getId().equals(l2.getId())) return false;
                            if (l1.getTimeslot() == null || l2.getTimeslot() == null) return false;
                            return l1.getTimeslot().getDayOfWeek().equals(l2.getTimeslot().getDayOfWeek()) &&
                                    !l1.getTimeslot().getTime().equals(l2.getTimeslot().getTime());
                        })
                )
                .filter((l1, l2) -> {
                    String t1 = l1.getTimeslot().getTime();
                    String t2 = l2.getTimeslot().getTime();
                    return Math.abs(getHourIndex(t1) - getHourIndex(t2)) > 1;
                })
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Avoid non-consecutive multiple subject lessons per day");
    }

    private int getHourIndex(String time) {
        switch (time) {
            case "08:00-09:00": return 1;
            case "09:00-10:00": return 2;
            case "10:00-11:00": return 3;
            case "11:00-12:00": return 4;
            case "12:00-13:00": return 5;
            default: return 0;
        }
    }
}

