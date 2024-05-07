package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserRegistrationDTO;
import com.openclassrooms.mddapi.dtos.UserResponseDTO;
import com.openclassrooms.mddapi.models.User;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Converts a UserRegistrationDTO object to a User object.
     *
     * @param dto The UserRegistrationDTO object to be converted.
     * @return The converted User object.
     */
    User userDtoToUser(UserRegistrationDTO dto);

    /**
     * Converts a User object to a UserResponseDTO object.
     *
     * @param user The User object to be converted.
     * @return The converted UserResponseDTO object.
     */
    UserResponseDTO userToUserResponseDTO(User user);

}
