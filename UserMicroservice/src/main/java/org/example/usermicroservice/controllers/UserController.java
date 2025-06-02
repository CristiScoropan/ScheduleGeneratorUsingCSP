package org.example.usermicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.usermicroservice.dtos.user.UserDTO;
import org.example.usermicroservice.dtos.user.UserRequestDTO;
import org.example.usermicroservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.usermicroservice.globals.UrlMappings.ID_PART;
import static org.example.usermicroservice.globals.UrlMappings.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public UserDTO create(@RequestBody UserRequestDTO userRequestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return userService.create(userRequestDTO, token);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(ID_PART)
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(ID_PART)
    public UserDTO update(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO){
        return userService.update(id, userRequestDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<String> getAllUsernames() {
        return userService.getAllUsernames();
    }
}
