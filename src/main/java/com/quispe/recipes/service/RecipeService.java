package com.quispe.recipes.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quispe.recipes.domain.dto.ErrorResponse;
import com.quispe.recipes.domain.dto.UpdateRecipeDTO;
import com.quispe.recipes.domain.entity.Recipe;
import com.quispe.recipes.domain.entity.RecipeListResponse;
import com.quispe.recipes.domain.exception.ExternalApiException;
import com.quispe.recipes.domain.exception.RecipeNotFoundException;

@Service
public class RecipeService {
  private final String baseUrl = "https://dummyjson.com/recipes";

  @Autowired
  private HttpClient client;

  @Autowired
  private ObjectMapper objectMapper;

  public List<Recipe> getAll() {
    try {
      HttpRequest request = HttpRequest
          .newBuilder(URI.create(baseUrl))
          .GET()
          .build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new ExternalApiException(objectMapper.readValue(response.body(), ErrorResponse.class).message());
      }

      return objectMapper.readValue(response.body(), RecipeListResponse.class).recipes();
    } catch (IOException | InterruptedException e) {
      throw new ExternalApiException("Error when get recipes: " + e.getMessage(), e);
    }
  }

  public Recipe getById(Long id) {
    try {
      HttpRequest request = HttpRequest
          .newBuilder(URI.create(String.format("%s/%s", baseUrl, id)))
          .GET()
          .build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new RecipeNotFoundException(objectMapper.readValue(response.body(), ErrorResponse.class).message());
      }

      return objectMapper.readValue(response.body(), Recipe.class);
    } catch (IOException | InterruptedException e) {
      throw new ExternalApiException("Error when get recipe by id: " + e.getMessage(), e);
    }
  }

  public List<Recipe> searchByName(String name) {
    try {
      HttpRequest request = HttpRequest
          .newBuilder(URI.create(String.format("%s/search?q=%s", baseUrl, name)))
          .GET()
          .build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new ExternalApiException(objectMapper.readValue(response.body(), ErrorResponse.class).message());
      }

      return objectMapper.readValue(response.body(), RecipeListResponse.class).recipes();
    } catch (IOException | InterruptedException e) {
      throw new ExternalApiException("Error when get recipes: " + e.getMessage(), e);
    }
  }

  public Recipe create(Recipe body) {
    try {
      String bodyAsString = objectMapper.writeValueAsString(body);
      HttpRequest request = HttpRequest
          .newBuilder(URI.create(String.format("%s/add", baseUrl)))
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(bodyAsString))
          .build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      if (response.statusCode() != 200) {
        throw new ExternalApiException(objectMapper.readValue(response.body(), ErrorResponse.class).message());
      }

      return objectMapper.readValue(response.body(), Recipe.class);
    } catch (IOException | InterruptedException e) {
      throw new ExternalApiException("Error when create a recipe: " + e.getMessage(), e);
    }
  }

  public Recipe update(Long id, UpdateRecipeDTO body) {
    try {
      System.out.println("ID " + id);
      String bodyAsString = objectMapper.writeValueAsString(body);
      HttpRequest request = HttpRequest
          .newBuilder(URI.create(String.format("%s/%s", baseUrl, id)))
          .header("Content-Type", "application/json")
          .PUT(HttpRequest.BodyPublishers.ofString(bodyAsString))
          .build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      if (response.statusCode() != 200) {
        throw new RecipeNotFoundException(objectMapper.readValue(response.body(), ErrorResponse.class).message());
      }

      return objectMapper.readValue(response.body(), Recipe.class);
    } catch (IOException | InterruptedException e) {
      throw new ExternalApiException("Error when update a recipe: " + e.getMessage(), e);
    }
  }

  public Recipe delete(Long id) {
    try {
      HttpRequest request = HttpRequest
          .newBuilder(URI.create(String.format("%s/%s", baseUrl, id)))
          .DELETE()
          .build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new RecipeNotFoundException(objectMapper.readValue(response.body(), ErrorResponse.class).message());
      }

      return objectMapper.readValue(response.body(), Recipe.class);
    } catch (IOException | InterruptedException e) {
      throw new ExternalApiException("Error when delete a recipe: " + e.getMessage(), e);
    }
  }
}
