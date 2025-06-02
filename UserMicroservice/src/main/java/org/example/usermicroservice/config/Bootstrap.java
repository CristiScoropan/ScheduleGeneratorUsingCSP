package org.example.usermicroservice.config;

import lombok.RequiredArgsConstructor;
import org.example.usermicroservice.dtos.auth.SignupRequest;
import org.example.usermicroservice.dtos.security.AuthService;
import org.example.usermicroservice.entities.ERole;
import org.example.usermicroservice.entities.Role;
import org.example.usermicroservice.entities.User;
import org.example.usermicroservice.repositories.RoleRepository;
import org.example.usermicroservice.repositories.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class Bootstrap {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @EventListener(ApplicationReadyEvent.class)
    public void bootstrapData(){
        for(ERole value: ERole.values()){
            if(roleRepository.findByName(value).isEmpty()){
                roleRepository.save(
                        Role.builder().name(value).build()
                );
            }
        }
        User user = userRepository.findByUsername("cristi").orElse(null);
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        if(user == null){
            SignupRequest user1 = SignupRequest.builder()
                    .username("cristi")
                    .password("admin")
                    .roles(roles)
                    .build();
            authService.register(user1);
        }
    }
}
