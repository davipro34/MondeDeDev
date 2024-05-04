package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserRegistrationDTO;
import com.openclassrooms.mddapi.dtos.UserResponseDTO;
import com.openclassrooms.mddapi.models.User;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * The UserMapper interface is responsible for mapping User entities to UserDTOs and vice versa.
     * It provides methods for converting User objects to UserDTO objects and vice versa.
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

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
