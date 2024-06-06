package com.openclassrooms.mddapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserResponseDTO;
import com.openclassrooms.mddapi.dtos.UserUpdatedResponseDTO;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.services.UserService;

/**
 * This class represents the UserController which handles user-related operations.
 */
@RestController
@RequestMapping("/me")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Constructs a new UserController with the specified UserService and UserMapper.
     *
     * @param userService the UserService to be used
     * @param userMapper the UserMapper to be used
     */
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Retrieves the current user.
     *
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing the UserResponseDTO of the current user
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        UserDTO userDTO = userService.getCurrentUser(authentication);
        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(userDTO);
        return ResponseEntity.ok(userResponseDTO);
    }


    /**
     * Updates the user with the provided UserDTO.
     *
     * @param userDTO        the UserDTO containing the updated user information
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing the updated UserUpdatedResponseDTO
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserUpdatedResponseDTO> updateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        UserUpdatedResponseDTO updatedUser = userService.updateUser(userDTO, authentication);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Subscribes the current user to a theme.
     *
     * @param themeId the ID of the theme to subscribe to
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing the updated UserDTO
     */
    @PostMapping("/themes/{themeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> subscribeToTheme(@PathVariable Long themeId, Authentication authentication) {
        UserDTO updatedUser = userService.subscribeToTheme(themeId, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    /**
     * Unsubscribes the current user from a theme.
     *
     * @param themeId the ID of the theme to unsubscribe from
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing the updated UserDTO
     */
    @DeleteMapping("/themes/{themeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> unsubscribeFromTheme(@PathVariable Long themeId, Authentication authentication) {
        UserDTO updatedUser = userService.unsubscribeFromTheme(themeId, authentication);
        return ResponseEntity.ok(updatedUser);
    }
}