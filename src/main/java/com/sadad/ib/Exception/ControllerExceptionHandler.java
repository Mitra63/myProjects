package com.sadad.ib.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestFormatException.class)
    public ResponseEntity<ErrorMessage> handleRequestFormatException(RequestFormatException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNumberNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAccountNumberNotFoundException(AccountNumberNotFoundException ex,
                                                                             WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                "You don't have permission to access this service",
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessage> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                "You don't have permission to access this service",
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotAccessException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotAccessException(ResourceNotAccessException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                new Date(),
                "You not permission for this operation",
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InvalidBalanceException.class)
    public ResponseEntity<ErrorMessage> handleInvalidBalanceException(InvalidBalanceException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AccountIsBlockedException.class)
    public ResponseEntity<ErrorMessage> handleAccountIsBlockedException(AccountIsBlockedException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDestinationException.class)
    public ResponseEntity<ErrorMessage> handleInvalidDestinationException(InvalidDestinationException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                "your request could not be processed",
                request.getDescription(false));
        log.error("An Exception Occurred:{}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
