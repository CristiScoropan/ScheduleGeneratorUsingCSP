package org.example.schedulemicroservice.repositories;

import org.example.schedulemicroservice.entities.ClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {
    Optional<ClassGroup> findByName(String name);
}
