package org.example.schedulemicroservice.config.security;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SignupRequest {
    private String username;
    private String password;
    private Set<String> roles;
}
