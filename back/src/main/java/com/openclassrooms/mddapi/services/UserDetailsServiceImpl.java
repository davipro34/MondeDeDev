package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Implementation of the UserDetailsService interface that loads user details by username.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user details by username or email.
     *
     * @param usernameOrEmail the username or email of the user
     * @return the user details for the given username or email
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user != null) {
            String identifier;
            if (user.getUsername() != null) {
                identifier = user.getUsername();
            } else {
                identifier = user.getEmail();
            }
            return org.springframework.security.core.userdetails.User.withUsername(identifier)
                    .password(user.getPassword())
                    .authorities(new ArrayList<>())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}