package com.quispe.recipes.domain.exception;

public class ExternalApiException extends RuntimeException {

  public ExternalApiException(String message) {
    super(message);
  }

  public ExternalApiException(String message, Throwable cause) {
    super(message, cause);
  }
}
