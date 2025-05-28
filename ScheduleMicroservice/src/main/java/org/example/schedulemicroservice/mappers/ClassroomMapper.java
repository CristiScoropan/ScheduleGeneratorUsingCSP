package org.example.schedulemicroservice.mappers;

import org.example.schedulemicroservice.dtos.ClassroomDTO;
import org.example.schedulemicroservice.entities.Classroom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    Classroom toEntity(ClassroomDTO classroomDTO);
    ClassroomDTO toDTO(Classroom classroom);
}
