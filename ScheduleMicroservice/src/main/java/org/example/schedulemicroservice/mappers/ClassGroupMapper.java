package org.example.schedulemicroservice.mappers;

import org.example.schedulemicroservice.dtos.ClassGroupDTO;
import org.example.schedulemicroservice.entities.ClassGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassGroupMapper {
    ClassGroup toEntity(ClassGroupDTO classGroupDTO);
    ClassGroupDTO toDTO(ClassGroup classGroup);
}
