package com.openclassrooms.mddapi.services;

import java.util.Optional;
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
import com.openclassrooms.mddapi.exceptions.EmailExistsException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

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

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
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
     * @throws IllegalArgumentException If the password does not meet the complexity requirements.
     */
    public User saveUser(User user) {
        try {
            validateUser(user);
            validatePasswordComplexity(user.getPassword());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // Encode the password before saving
            User savedUser = userRepository.save(user);
            return savedUser;
        } catch (DataIntegrityViolationException e) {
            throw new EmailExistsException("A user with this email already exists.");
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
    * Authenticates the user with the provided login credentials.
    *
    * @param loginDTO The login data transfer object containing the user's email and password.
    * @return The authenticated user.
    * @throws UsernameNotFoundException If the email or username is invalid.
    * @throws BadCredentialsException If the password is invalid.
    */
    public Authentication authenticate(LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or username");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid password");
        }
    }
}
