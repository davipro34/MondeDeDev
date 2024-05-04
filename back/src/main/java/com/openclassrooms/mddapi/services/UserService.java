package com.openclassrooms.mddapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exceptions.EmailExistsException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.Data;

/**
 * This class represents a service for managing user operations.
 */
@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a user in the database.
     * 
     * @param user The user to be saved.
     * @return The saved user.
     * @throws EmailExistsException If a user with the same email already exists.
     */
    public User saveUser(User user) {
        try {
            User savedUser = userRepository.save(user);
            return savedUser;
        } catch (DataIntegrityViolationException e) {
            throw new EmailExistsException("A user with this email already exists.");
        }
    }
}
