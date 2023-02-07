package ru.alex.braim.controllerExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.alex.braim.exception.NotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private ResponseEntity<ErrorMessage> getResponseEntityWithStatus(HttpStatus status, RuntimeException exception) {
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundExceptionHandler(RuntimeException exception) {
        return getResponseEntityWithStatus(HttpStatus.NOT_FOUND, exception);
    }
}
