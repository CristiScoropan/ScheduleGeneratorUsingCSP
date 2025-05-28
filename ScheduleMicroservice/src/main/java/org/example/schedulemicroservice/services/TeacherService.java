package org.example.schedulemicroservice.services;

import lombok.AllArgsConstructor;
import org.example.schedulemicroservice.dtos.TeacherDTO;
import org.example.schedulemicroservice.entities.Teacher;
import org.example.schedulemicroservice.mappers.TeacherMapper;
import org.example.schedulemicroservice.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherService {
    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;

    public List<TeacherDTO> getAllTeachers(){
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TeacherDTO create(TeacherDTO teacherDTO){
        return teacherMapper.toDTO(teacherRepository.save(teacherMapper.toEntity(teacherDTO)));
    }

    public void delete(Long id){
        teacherRepository.deleteById(id);
    }

    public TeacherDTO update(Long id, TeacherDTO teacherDTO){
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if(optionalTeacher.isPresent()){
            Teacher teacher = optionalTeacher.get();
            teacher.setName(teacherDTO.getName());
            return teacherMapper.toDTO(teacherRepository.save(teacher));
        }else{
            throw new RuntimeException("Teacher not found with id: " + id);
        }
    }

    public Teacher getTeacherByName(String name) {
        return teacherRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Teacher not found with name: " + name));
    }
}
