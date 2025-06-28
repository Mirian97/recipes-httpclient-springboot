package com.quispe.recipes.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.quispe.recipes.domain.dto.ErrorResponse;
import com.quispe.recipes.domain.exception.ExternalApiException;
import com.quispe.recipes.domain.exception.RecipeNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ExternalApiException.class)
  public ResponseEntity<ErrorResponse> handleExternalApiException(ExternalApiException e) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(RecipeNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleRecipeNotFoundException(RecipeNotFoundException e) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(e.getMessage()));
  }
}
