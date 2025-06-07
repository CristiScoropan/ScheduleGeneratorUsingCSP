package org.example.schedulemicroservice.repositories;

import org.example.schedulemicroservice.entities.UserReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReferenceRepository extends JpaRepository<UserReference, Long> {
}
