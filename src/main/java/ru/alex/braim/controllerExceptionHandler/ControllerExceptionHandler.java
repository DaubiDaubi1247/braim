package ru.alex.braim.controllerExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.alex.braim.exception.*;

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

    @ExceptionHandler({BadRequestData.class, ConstraintViolationException.class, ConnectionWithAnimal.class})
    public ResponseEntity<ErrorMessage> badRequestData(RuntimeException exception) {
        return getResponseEntityWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<ErrorMessage> alreadyExist(RuntimeException exception) {
        return getResponseEntityWithStatus(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler({NotEqualsAccounts.class})
    public ResponseEntity<ErrorMessage> notEqualsAccountsInfo(RuntimeException exception) {
        return getResponseEntityWithStatus(HttpStatus.FORBIDDEN, exception);
    }
}
