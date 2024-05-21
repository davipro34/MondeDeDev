package com.openclassrooms.mddapi.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * This class is a controller advice that handles global exceptions thrown by the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the EmailExistsException and returns a ResponseEntity with the error message.
     *
     * @param e The EmailExistsException to handle.
     * @return A ResponseEntity with the error message and HTTP status code.
     */
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<String> handleEmailExistsException(EmailExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles the UsernameExistsException and returns a ResponseEntity with the error message.
     *
     * @param e The UsernameExistsException to handle.
     * @return A ResponseEntity with the error message and HTTP status code.
     */
    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<String> handleUsernameExistsException(UsernameExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles MethodArgumentNotValidException and returns a ResponseEntity containing a list of validation errors.
     *
     * @param ex The MethodArgumentNotValidException to handle.
     * @return A ResponseEntity containing a list of validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors()
            .stream()
            .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles the IllegalArgumentException and returns a ResponseEntity with a bad request status code and the error message.
     *
     * @param e The IllegalArgumentException that was thrown
     * @return A ResponseEntity with a bad request status code and the error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles the UsernameNotFoundException and returns a ResponseEntity with the error message.
     *
     * @param e The UsernameNotFoundException to handle.
     * @return A ResponseEntity with the error message and HTTP status code.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handles the BadCredentialsException and returns a ResponseEntity with the error message.
     *
     * @param e The BadCredentialsException to handle.
     * @return A ResponseEntity with the error message and HTTP status code.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Handles the exception thrown when authentication fails.
     *
     * @param e The AuthenticationException that was thrown
     * @return A ResponseEntity with the HTTP status code 401 (UNAUTHORIZED) and the error message from the exception
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
