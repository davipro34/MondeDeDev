package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dtos.UserRegistrationDTO;
import com.openclassrooms.mddapi.dtos.UserResponseDTO;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
    * Registers a new user.
    *
    * @param userRegistrationDTO The user registration data.
    * @return The response entity containing the user response DTO.
    */
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {        
        User user = UserMapper.INSTANCE.userDtoToUser(userRegistrationDTO); 
        User savedUser = userService.saveUser(user);
        UserResponseDTO responseDTO = UserMapper.INSTANCE.userToUserResponseDTO(savedUser);
         return ResponseEntity.ok(responseDTO);
    }
}
