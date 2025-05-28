package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.TeacherDTO;
import org.example.schedulemicroservice.services.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PostMapping
    public TeacherDTO create(TeacherDTO teacherDTO) {
        return teacherService.create(teacherDTO);
    }

    @PutMapping("/{id}")
    public TeacherDTO update(Long id, TeacherDTO teacherDTO) {
        return teacherService.update(id, teacherDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }

}
