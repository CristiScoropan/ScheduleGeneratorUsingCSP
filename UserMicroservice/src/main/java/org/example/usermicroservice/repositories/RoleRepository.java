package org.example.usermicroservice.repositories;

import org.example.usermicroservice.entities.ERole;
import org.example.usermicroservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
