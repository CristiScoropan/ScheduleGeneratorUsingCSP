package org.example.schedulemicroservice.services;

import lombok.AllArgsConstructor;
import org.example.schedulemicroservice.dtos.SubjectDTO;
import org.example.schedulemicroservice.entities.Subject;
import org.example.schedulemicroservice.mappers.SubjectMapper;
import org.example.schedulemicroservice.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;

    public List<SubjectDTO> getAllSubjects(){
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SubjectDTO create(SubjectDTO subjectDTO){
        return subjectMapper.toDTO(subjectRepository.save(subjectMapper.toEntity(subjectDTO)));
    }

    public void delete(Long id){
        subjectRepository.deleteById(id);
    }

    public SubjectDTO update(Long id, SubjectDTO subjectDTO){
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if(optionalSubject.isPresent()){
            Subject subject = optionalSubject.get();
            subject.setName(subjectDTO.getName());
            subject.setTeacher(subjectDTO.getTeacher());
            return subjectMapper.toDTO(subjectRepository.save(subject));
        }else{
            throw new RuntimeException("Subject not found with id: " + id);
        }
    }

    public Subject getSubjectByName(String name) {
        return subjectRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Subject not found with name: " + name));
    }
}
