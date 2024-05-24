package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserRegistrationDTO;
import com.openclassrooms.mddapi.dtos.UserResponseDTO;
import com.openclassrooms.mddapi.models.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a UserRegistrationDTO object to a User object.
     *
     * @param dto The UserRegistrationDTO object to be converted.
     * @return The converted User object.
     */
    @Mapping(target = "id", ignore = true)
    User userDtoToUser(UserRegistrationDTO dto);

    /**
     * Converts a User object to a UserResponseDTO object.
     *
     * @param user The User object to be converted.
     * @return The converted UserResponseDTO object.
     */
    UserResponseDTO userToUserResponseDTO(User user);

    /**
     * Converts a User object to a UserDTO object.
     *
     * @param user The User object to be converted.
     * @return The converted UserDTO object.
     */
    UserDTO toDTO(User user);

    /**
     * Converts a UserDTO object to a User object.
     *
     * @param dto The UserDTO object to be converted.
     * @return The converted User object.
     */
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO userDTO);

}
