package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.SubjectDTO;
import org.example.schedulemicroservice.services.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @PostMapping
    public SubjectDTO create(SubjectDTO subjectDTO) {
        return subjectService.create(subjectDTO);
    }

    @PutMapping("/{id}")
    public SubjectDTO update(Long id, SubjectDTO subjectDTO) {
        return subjectService.update(id, subjectDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        subjectService.delete(id);
    }
}
