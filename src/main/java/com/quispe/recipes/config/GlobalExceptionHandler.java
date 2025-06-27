package com.quispe.recipes.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.quispe.recipes.domain.exception.ExternalApiException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ExternalApiException.class)
  public ResponseEntity<String> handleExternalApiException(ExternalApiException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }
}
