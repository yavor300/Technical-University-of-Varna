package bg.tuvarna.sit.controllers;

import bg.tuvarna.sit.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception exception) {
    ErrorDto errorDto = new ErrorDto(LocalDateTime.now(), exception.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
}
