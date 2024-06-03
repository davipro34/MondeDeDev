package com.openclassrooms.mddapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dtos.LoginDTO;
import com.openclassrooms.mddapi.dtos.TokenResponseDTO;
import com.openclassrooms.mddapi.dtos.UserRegistrationDTO;
import com.openclassrooms.mddapi.dtos.UserResponseDTO;
import com.openclassrooms.mddapi.mappers.TokenMapper;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.security.JWTService;
import com.openclassrooms.mddapi.services.UserService;

/**
 * The AuthController class handles authentication-related operations.
 */
@RestController
public class AuthController {

    private final UserService userService;
    private final JWTService jwtService;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;

    public AuthController(UserService userService, JWTService jwtService, UserMapper userMapper, TokenMapper tokenMapper) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.tokenMapper = tokenMapper;
    }

    /**
     * Registers a new user.
     *
     * @param userRegistrationDTO The user registration data.
     * @return The response entity containing the user response DTO.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {        
        User user = userMapper.userDtoToUser(userRegistrationDTO); 
        User savedUser = userService.saveUser(user);
        UserResponseDTO responseDTO = userMapper.userToUserResponseDTO(savedUser);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginDTO The login credentials of the user.
     * @return A ResponseEntity containing the generated token.
     */
    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO) throws AuthenticationException {
        Authentication authentication = userService.authenticate(loginDTO);
        String token = jwtService.generateToken(authentication);
        TokenResponseDTO tokenResponseDTO = tokenMapper.tokenToTokenResponseDTO(token);
        return ResponseEntity.ok(tokenResponseDTO);
    }
}
