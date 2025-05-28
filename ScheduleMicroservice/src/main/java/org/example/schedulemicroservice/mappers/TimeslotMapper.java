package org.example.schedulemicroservice.mappers;

import org.example.schedulemicroservice.dtos.TimeslotDTO;
import org.example.schedulemicroservice.entities.Timeslot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeslotMapper {
    Timeslot toEntity(TimeslotDTO timeslotDTO);
    TimeslotDTO toDTO(Timeslot timeslot);
}
