package org.example.usermicroservice.mappers;

import org.example.usermicroservice.dtos.user.UserDTO;
import org.example.usermicroservice.dtos.user.UserRequestDTO;
import org.example.usermicroservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO entityToDTO(User user);
    User DTOToEntity(UserRequestDTO userRequestDTO);
}
