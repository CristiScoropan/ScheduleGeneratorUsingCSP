package org.example.schedulemicroservice.mappers;

import org.example.schedulemicroservice.dtos.SubjectDTO;
import org.example.schedulemicroservice.entities.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    Subject toEntity(SubjectDTO subjectDTO);
    SubjectDTO toDTO(Subject subject);
}
