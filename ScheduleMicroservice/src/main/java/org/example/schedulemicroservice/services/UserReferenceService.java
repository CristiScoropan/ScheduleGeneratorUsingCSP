package org.example.schedulemicroservice.services;

import lombok.RequiredArgsConstructor;
import org.example.schedulemicroservice.entities.UserReference;
import org.example.schedulemicroservice.repositories.UserReferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserReferenceService {
    private final UserReferenceRepository userReferenceRepository;
    private final RestTemplate restTemplate;

    public UserReference fetchAndSaveUserReference(Long userId){
        String url = "http://localhost:8081/api/users/" + userId;
        UserReference userReference = restTemplate.getForObject(url, UserReference.class);
        if(userReference == null){
            throw new RuntimeException("User not found with ID: " + userId);
        }
        return userReferenceRepository.save(userReference);
    }
}
