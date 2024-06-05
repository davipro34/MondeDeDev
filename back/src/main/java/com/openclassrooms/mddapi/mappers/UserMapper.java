package com.openclassrooms.mddapi.mappers;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserRegistrationDTO;
import com.openclassrooms.mddapi.dtos.UserResponseDTO;
import com.openclassrooms.mddapi.models.User;

@Component
public class UserMapper {

    /**
     * Converts a UserRegistrationDTO to a User entity.
     *
     * @param dto The UserRegistrationDTO to convert.
     * @return The converted User entity, or null if the input dto is null.
     */
    public User userDtoToUser(UserRegistrationDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    /**
     * Converts a User entity to a UserResponseDTO.
     *
     * @param user The User entity to convert.
     * @return The converted UserResponseDTO, or null if the input user is null.
     */
    public UserResponseDTO userToUserResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        return responseDTO;
    }

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user The User entity to convert.
     * @return The converted UserDTO, or null if the input user is null.
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .themeIds(user.getThemeIds())
                .build();
    }

    /**
     * Converts a UserDTO to a User entity.
     *
     * @param userDTO The UserDTO to convert.
     * @return The converted User entity, or null if the input userDTO is null.
     */
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
