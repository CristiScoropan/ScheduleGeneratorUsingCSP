package org.example.schedulemicroservice.services;

import lombok.AllArgsConstructor;
import org.example.schedulemicroservice.dtos.TimeslotDTO;
import org.example.schedulemicroservice.entities.Timeslot;
import org.example.schedulemicroservice.mappers.TimeslotMapper;
import org.example.schedulemicroservice.repositories.TimeslotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeslotService {
    private final TimeslotMapper timeslotMapper;
    private final TimeslotRepository timeslotRepository;

    public List<TimeslotDTO> getAllTimeslots() {
        return timeslotRepository.findAll().stream()
                .map(timeslotMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TimeslotDTO create(TimeslotDTO timeslotDTO){
        return timeslotMapper.toDTO(timeslotRepository.save(timeslotMapper.toEntity(timeslotDTO)));
    }

    public void delete(Long id){
        timeslotRepository.deleteById(id);
    }

    public TimeslotDTO update(Long id, TimeslotDTO timeslotDTO){
        Optional<Timeslot> optionalTimeslot = timeslotRepository.findById(id);
        if(optionalTimeslot.isPresent()){
            Timeslot timeslot = optionalTimeslot.get();
            timeslot.setTime(timeslotDTO.getTime());
            timeslot.setDayOfWeek(timeslot.getDayOfWeek());
            return timeslotMapper.toDTO(timeslotRepository.save(timeslot));
        }else{
            throw new RuntimeException("Timeslot not found with id: " + id);
        }
    }

    public Timeslot getTimeslotByDayAndTime(String dayOfWeek, String time) {
        return timeslotRepository.findByDayOfWeekAndTime(dayOfWeek, time)
                .orElseThrow(() -> new RuntimeException("Timeslot not found for day: " + dayOfWeek + " and time: " + time));
    }
}
