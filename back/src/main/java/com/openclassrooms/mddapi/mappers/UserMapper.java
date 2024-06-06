package com.openclassrooms.mddapi.mappers;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserRegistrationDTO;
import com.openclassrooms.mddapi.dtos.UserRegistrationResponseDTO;
import com.openclassrooms.mddapi.dtos.UserResponseDTO;
import com.openclassrooms.mddapi.dtos.UserUpdatedResponseDTO;
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
     * @param userDTO The User entity to convert.
     * @return The converted UserResponseDTO, or null if the input user is null.
     */
    public UserResponseDTO userToUserResponseDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(userDTO.getId());
        responseDTO.setUsername(userDTO.getUsername());
        responseDTO.setEmail(userDTO.getEmail());
        responseDTO.setSubscribedThemeIds(userDTO.getSubscribedThemeIds());
        return responseDTO;
    }

    /**
     * Converts a User entity to a UserRegistrationResponseDTO.
     *
     * @param user The User entity to convert.
     * @return The converted UserRegistrationResponseDTO, or null if the input user is null.
     */
    public UserRegistrationResponseDTO userToUserRegistrationResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        UserRegistrationResponseDTO responseDTO = new UserRegistrationResponseDTO();
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
                .subscribedThemeIds(user.getThemeIds())
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

    /**
     * Converts a UserDTO to a UserUpdatedResponseDTO.
     *
     * @param userDTO The UserDTO to convert.
     * @return The converted UserUpdatedResponseDTO, or null if the input userDTO is null.
     */
    public UserUpdatedResponseDTO userToUserUpdatedResponseDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        UserUpdatedResponseDTO responseDTO = new UserUpdatedResponseDTO();
        responseDTO.setId(userDTO.getId());
        responseDTO.setUsername(userDTO.getUsername());
        responseDTO.setEmail(userDTO.getEmail());
        responseDTO.setSubscribedThemeIds(userDTO.getSubscribedThemeIds());
        return responseDTO;
    }
}
