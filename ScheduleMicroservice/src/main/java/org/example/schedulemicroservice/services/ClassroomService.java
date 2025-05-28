package org.example.schedulemicroservice.services;

import lombok.AllArgsConstructor;
import org.example.schedulemicroservice.dtos.ClassroomDTO;
import org.example.schedulemicroservice.entities.Classroom;
import org.example.schedulemicroservice.mappers.ClassroomMapper;
import org.example.schedulemicroservice.repositories.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassroomService {
    private final ClassroomMapper classroomMapper;
    private final ClassroomRepository classroomRepository;

    public List<ClassroomDTO> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(classroomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClassroomDTO create(ClassroomDTO classroomDTO) {
        return classroomMapper.toDTO(classroomRepository.save(classroomMapper.toEntity(classroomDTO)));
    }

    public void delete(Long id) {
        classroomRepository.deleteById(id);
    }

    public ClassroomDTO update(Long id, ClassroomDTO classroomDTO){
        Optional<Classroom> optionalClassroom = classroomRepository.findById(id);
        if(optionalClassroom.isPresent()){
            Classroom classroom = optionalClassroom.get();
            classroom.setName(classroomDTO.getName());
            classroom.setCapacity(classroomDTO.getCapacity());
            return classroomMapper.toDTO(classroomRepository.save(classroom));
        } else{
            throw new RuntimeException("Classroom not found with id: " + id);
        }
    }

    public Classroom getClassroomByName(String name) {
        return classroomRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Classroom not found with name: " + name));
    }
}
