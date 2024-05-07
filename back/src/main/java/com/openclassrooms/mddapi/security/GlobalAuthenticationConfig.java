package com.openclassrooms.mddapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

/**
 * This class provides the global authentication configuration for the application.
 */
@Configuration
public class GlobalAuthenticationConfig {

    /**
     * Creates and configures the authentication manager bean.
     *
     * @param authenticationConfiguration The authentication configuration to use.
     * @return The configured authentication manager.
     * @throws Exception If an error occurs while configuring the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}