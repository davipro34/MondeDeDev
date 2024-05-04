package com.openclassrooms.mddapi.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Saves a user in the system.
     *
     * @param user The user to be saved.
     * @return The saved user.
     * @throws EmailExistsException If a user with the same email already exists.
     */
    public User saveUser(User user) {
        try {
            validateUser(user);
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
}
