package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.ClassGroupDTO;
import org.example.schedulemicroservice.services.ClassGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/classGroups")
public class ClassGroupController {
    private final ClassGroupService classGroupService;

    @GetMapping
    public List<ClassGroupDTO> getAllClassGroups() {
        return classGroupService.getAllClassGroups();
    }

    @PostMapping
    public ClassGroupDTO create(ClassGroupDTO classGroupDTO) {
        return classGroupService.create(classGroupDTO);
    }

    @PutMapping("/{id}")
    public ClassGroupDTO update(Long id, ClassGroupDTO classGroupDTO) {
        return classGroupService.update(id, classGroupDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        classGroupService.delete(id);
    }
}
