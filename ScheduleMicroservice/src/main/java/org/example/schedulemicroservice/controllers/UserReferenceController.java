package org.example.schedulemicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.dtos.UserReferenceDTO;
import org.example.schedulemicroservice.entities.UserReference;
import org.example.schedulemicroservice.repositories.UserReferenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mapping")
public class UserReferenceController {

    private final UserReferenceRepository userReferenceRepository;

    @PostMapping
    public ResponseEntity<UserReferenceDTO> createMapping(@RequestBody UserReferenceDTO userReferenceDTO){
        UserReference userReference = UserReference.builder()
                .username(userReferenceDTO.getUsername())
                .build();
        userReferenceRepository.save(userReference);
        return ResponseEntity.ok(userReferenceDTO);
    }
}
