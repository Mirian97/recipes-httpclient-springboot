package com.quispe.recipes.domain.exception;

public class RecipeNotFoundException extends RuntimeException {

  public RecipeNotFoundException(String message) {
    super(message);
  }
}
