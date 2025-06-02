package org.example.usermicroservice.services;

import lombok.AllArgsConstructor;
import org.example.usermicroservice.dtos.user.UserDTO;
import org.example.usermicroservice.dtos.user.UserReferenceDTO;
import org.example.usermicroservice.dtos.user.UserRequestDTO;
import org.example.usermicroservice.entities.User;
import org.example.usermicroservice.mappers.UserMapper;
import org.example.usermicroservice.repositories.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO create(UserRequestDTO userRequestDTO, String token){
        User user = userMapper.DTOToEntity(userRequestDTO);
        User savedUser = userRepository.save(user);
        UserReferenceDTO userReferenceDTO = UserReferenceDTO.builder()
                .userId(savedUser.getId())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.replace("Bearer ", ""));

        HttpEntity<UserReferenceDTO> requestEntity = new HttpEntity<>(userReferenceDTO, headers);
        ResponseEntity<UserReferenceDTO> response = restTemplate.postForEntity(
                "http://localhost:8080/api/mapping",
                userReferenceDTO,
                UserReferenceDTO.class
        );
        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create user mapping" + response.getStatusCode());
        }
        return userMapper.entityToDTO(savedUser);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public UserDTO update(Long id, UserRequestDTO userRequestDTO){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userRequestDTO.getUsername());
            User updatedUser = userRepository.save(user);
            return userMapper.entityToDTO(updatedUser);
        } else{
            return null;
        }
    }

    public List<String> getAllUsernames() {
        return userRepository.findAll().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    public UserDTO findByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return userMapper.entityToDTO(user);
    }
}
