package org.example.schedulemicroservice.mappers;

import org.example.schedulemicroservice.dtos.TeacherDTO;
import org.example.schedulemicroservice.entities.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    Teacher toEntity(TeacherDTO teacherDTO);
    TeacherDTO toDTO(Teacher teacher);
}
