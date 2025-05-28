package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.TimeslotDTO;
import org.example.schedulemicroservice.services.TimeslotService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/timeslots")
public class TimeslotController {
    private final TimeslotService timeslotService;

    @GetMapping
    public List<TimeslotDTO> getAllTimeslots() {
        return timeslotService.getAllTimeslots();
    }

    @PostMapping
    public TimeslotDTO create(TimeslotDTO timeslotDTO) {
        return timeslotService.create(timeslotDTO);
    }

    @PutMapping("/{id}")
    public TimeslotDTO update(Long id, TimeslotDTO timeslotDTO) {
        return timeslotService.update(id, timeslotDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        timeslotService.delete(id);
    }
}
