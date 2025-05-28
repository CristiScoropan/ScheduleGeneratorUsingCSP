package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.ClassroomDTO;
import org.example.schedulemicroservice.services.ClassroomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    @GetMapping
    public List<ClassroomDTO> getAllClassrooms() {
        return classroomService.getAllClassrooms();
    }

    @PostMapping
    public ClassroomDTO create(@RequestBody ClassroomDTO classroomDTO) {
        return classroomService.create(classroomDTO);
    }

    @PutMapping("/{id}")
    public ClassroomDTO update(@PathVariable Long id, @RequestBody ClassroomDTO classroomDTO) {
        return classroomService.update(id, classroomDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        classroomService.delete(id);
    }
}
