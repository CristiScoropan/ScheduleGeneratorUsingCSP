package org.example.schedulemicroservice.mappers;

import org.example.schedulemicroservice.dtos.ScheduleDTO;
import org.example.schedulemicroservice.entities.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule toEntity(ScheduleDTO scheduleDTO);
    ScheduleDTO toDTO(Schedule schedule);
}
