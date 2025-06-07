package org.example.schedulemicroservice.mappers;

import org.example.schedulemicroservice.dtos.ScheduleDTO;
import org.example.schedulemicroservice.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleDTO toDTO(Schedule schedule);
    Schedule toEntity(ScheduleDTO scheduleDTO);

    // Add these custom type conversions:
    default String map(Subject subject) {
        return subject != null ? subject.getName() : null;
    }

    default Subject mapSubject(String name) {
        Subject subject = new Subject();
        subject.setName(name);
        return subject;
    }

    default String map(Teacher teacher) {
        return teacher != null ? teacher.getName() : null;
    }

    default Teacher mapTeacher(String name) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        return teacher;
    }

    default String map(ClassGroup classGroup) {
        return classGroup != null ? classGroup.getName() : null;
    }

    default ClassGroup mapClassGroup(String name) {
        ClassGroup cg = new ClassGroup();
        cg.setName(name);
        return cg;
    }

    default String map(Classroom classroom) {
        return classroom != null ? classroom.getName() : null;
    }

    default Classroom mapClassroom(String name) {
        Classroom classroom = new Classroom();
        classroom.setName(name);
        return classroom;
    }

    default String map(Timeslot timeslot) {
        return timeslot != null ? timeslot.getDayOfWeek() + " " + timeslot.getTime() : null;
    }

    default Timeslot mapTimeslot(String input) {
        String[] parts = input.split(" ");
        if (parts.length == 2) {
            Timeslot timeslot = new Timeslot();
            timeslot.setDayOfWeek(parts[0]);
            timeslot.setTime(parts[1]);
            return timeslot;
        }
        return null;
    }
}
