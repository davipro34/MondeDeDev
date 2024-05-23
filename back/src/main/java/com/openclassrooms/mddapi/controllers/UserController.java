package com.openclassrooms.mddapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.services.UserService;

/**
 * This class represents the UserController which handles user-related operations.
 */
@RestController
@RequestMapping("/me")
public class UserController {
    private final UserService userService;

    /**
     * Constructs a new UserController with the specified UserService.
     *
     * @param userService the UserService to be used
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the current user.
     *
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing the UserDTO of the current user
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        UserDTO userDTO = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Updates the user with the provided UserDTO.
     *
     * @param userDTO        the UserDTO containing the updated user information
     * @param authentication the authentication object representing the current user
     * @return the updated UserDTO
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        return userService.updateUser(userDTO, authentication);
    }
}