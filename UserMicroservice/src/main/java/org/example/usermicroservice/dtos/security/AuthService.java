package org.example.usermicroservice.dtos.security;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.usermicroservice.dtos.auth.SignupRequest;
import org.example.usermicroservice.dtos.user.UserReferenceDTO;
import org.example.usermicroservice.entities.ERole;
import org.example.usermicroservice.entities.Role;
import org.example.usermicroservice.entities.User;
import org.example.usermicroservice.repositories.RoleRepository;
import org.example.usermicroservice.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void register(SignupRequest signupRequest){
        User user = User.builder()
                .username(signupRequest.getUsername())
                .password(signupRequest.getPassword())
                .build();

        Set<String> roleStr = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if(roleStr == null || roleStr.isEmpty()){
            Role defaultRole = roleRepository.findByName(ERole.STUDENT)
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find STUDENT role"));
            roles.add(defaultRole);
        } else {
            roleStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new EntityNotFoundException("Cannot find role: " + r));
                roles.add(ro);
            });
        }
        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        UserReferenceDTO userReferenceDTO = UserReferenceDTO.builder()
                .userId(savedUser.getId())
                .build();

        ResponseEntity<UserReferenceDTO> response = restTemplate.postForEntity(
                "http://localhost:8081/api/mapping",
                userReferenceDTO,
                UserReferenceDTO.class
        );
    }

    public Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
