package com.openclassrooms.mddapi.services;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dtos.LoginDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.EmailExistsException;
import com.openclassrooms.mddapi.exceptions.UsernameExistsException;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Data;

/**
 * This class represents a service for managing user operations.
 */
@Data
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    /**
     * Validates the complexity of a password.
     * The password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one punctuation mark.
     *
     * @param password The password to validate.
     * @throws IllegalArgumentException If the password does not meet the complexity requirements.
     */
    private void validatePasswordComplexity(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct}).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one punctuation mark.");
        }
    }

    /**
     * Saves a user in the system.
     * Before saving, the user is validated and the password is checked for complexity and then encoded.
     *
     * @param user The user to be saved.
     * @return The saved user.
     * @throws EmailExistsException If a user with the same email already exists.
     * @throws UsernameExistsException If a user with the same username already exists.
     * @throws IllegalArgumentException If the password does not meet the complexity requirements.
     */
    public User saveUser(User user) {
        try {
            validateUser(user);
            validatePasswordComplexity(user.getPassword());
            if (userRepository.findByUsername(user.getUsername()) != null) {
                throw new UsernameExistsException("A user with this username already exists.");
            }
            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new EmailExistsException("A user with this email already exists.");
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // Encode the password before saving
            User savedUser = userRepository.save(user);
            return savedUser;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while saving the user.");
        }
    }

    /**
     * Validates a User object using the default validator.
     * If any validation violations are found, an IllegalArgumentException is thrown with the error message.
     *
     * @param user The User object to validate.
     * @throws IllegalArgumentException If any validation violations are found.
     */
    private void validateUser(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Authenticates a user based on the provided login credentials.
     *
     * @param loginDTO The login data transfer object containing the username or email and password.
     * @return The authenticated user.
     * @throws UsernameNotFoundException If the provided email or username is invalid.
     * @throws BadCredentialsException If the provided password is invalid.
     */
    public Authentication authenticate(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getUsernameOrEmail());
        if (user == null) {
            user = userRepository.findByUsername(loginDTO.getUsernameOrEmail());
        }
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or username");
        }
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid password");
        }
    }

    /**
     * Retrieves the current authenticated user and converts it to a UserDTO.
     *
     * @param authentication The authentication object containing the user's credentials.
     * @return The UserDTO of the current authenticated user.
     * @throws UsernameNotFoundException If the user is not found.
     */
    public UserDTO getCurrentUser(Authentication authentication) {
        String emailOrUsername = authentication.getName();
        User user = userRepository.findByEmail(emailOrUsername);
        if (user == null) {
            user = userRepository.findByUsername(emailOrUsername);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userMapper.toDTO(user);
    }

    /**
     * Updates the current authenticated user's information.
     *
     * @param userDTO The UserDTO containing the updated user information.
     * @param authentication The authentication object containing the user's credentials.
     * @return The updated UserDTO.
     * @throws EntityNotFoundException If the user is not found.
     */
    public UserDTO updateUser(UserDTO userDTO, Authentication authentication) {
        UserDTO currentUserDTO = getCurrentUser(authentication);
        User user = userRepository.findById(currentUserDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
}
