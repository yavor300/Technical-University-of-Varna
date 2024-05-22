package bg.tuvarna.sit.controllers;

import bg.tuvarna.sit.dto.ErrorDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {

    ErrorDto errors = new ErrorDto(LocalDateTime.now(), new ArrayList<>());
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String errorMessage = error.getDefaultMessage();
      errors.addError(errorMessage);
    });

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException ex) {
    ErrorDto errorDto = new ErrorDto(LocalDateTime.now(), List.of(ex.getMessage()));
    return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception exception) {
    ErrorDto errorDto = new ErrorDto(LocalDateTime.now(), List.of(exception.getMessage()));
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
}
