package org.example.schedulemicroservice.services;

import lombok.AllArgsConstructor;
import org.example.schedulemicroservice.dtos.ClassGroupDTO;
import org.example.schedulemicroservice.entities.ClassGroup;
import org.example.schedulemicroservice.mappers.ClassGroupMapper;
import org.example.schedulemicroservice.repositories.ClassGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassGroupService {
    private final ClassGroupMapper classGroupMapper;
    private final ClassGroupRepository classGroupRepository;

    public List<ClassGroupDTO> getAllClassGroups() {
        return classGroupRepository.findAll().stream()
                .map(classGroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClassGroupDTO create(ClassGroupDTO classGroupDTO) {
        return classGroupMapper.toDTO(classGroupRepository.save(classGroupMapper.toEntity(classGroupDTO)));
    }

    public void delete(Long id) {
        classGroupRepository.deleteById(id);
    }

    public ClassGroupDTO update(Long id, ClassGroupDTO classGroupDTO) {
        Optional<ClassGroup> optionalClassGroup = classGroupRepository.findById(id);
        if(optionalClassGroup.isPresent()){
            ClassGroup classGroup = optionalClassGroup.get();
            classGroup.setName(classGroupDTO.getName());
            return classGroupMapper.toDTO(classGroupRepository.save(classGroup));
        } else {
            throw new RuntimeException("Class group not found with id: " + id);
        }
    }

    public ClassGroup getClassGroupByName(String name) {
        return classGroupRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Class group not found with name: " + name));
    }
}
