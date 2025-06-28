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
    String url = baseUrl;
    return sendRequest(url, "GET", null, RecipeListResponse.class).recipes();
  }

  public Recipe getById(Long id) {
    String url = String.format("%s/%s", baseUrl, id);
    return sendRequest(url, "GET", null, Recipe.class);
  }

  public List<Recipe> searchByName(String name) {
    String url = String.format("%s/search?q=%s", baseUrl, name);
    return sendRequest(url, "GET", null, RecipeListResponse.class).recipes();
  }

  public Recipe create(Recipe body) {
    String url = String.format("%s/add", baseUrl);
    return sendRequest(url, "POST", body, Recipe.class);
  }

  public Recipe update(Long id, UpdateRecipeDTO body) {
    String url = String.format("%s/%s", baseUrl, id);
    return sendRequest(url, "PUT", body, Recipe.class);
  }

  public Recipe delete(Long id) {
    String url = String.format("%s/%s", baseUrl, id);
    return sendRequest(url, "DELETE", null, Recipe.class);
  }

  private <T> T sendRequest(String url, String method, Object body, Class<T> responseType) {
    try {
      HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(url)).header("Content-Type", "application/json");

      switch (method) {
        case "POST":
          builder.POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)));
          break;
        case "PUT":
          builder.PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)));
          break;
        case "DELETE":
          builder.DELETE();
          break;
        default:
          builder.GET();
      }

      HttpResponse<String> response = client.send(builder.build(), BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        ErrorResponse error = objectMapper.readValue(response.body(), ErrorResponse.class);
        if (response.statusCode() == 404) {
          throw new RecipeNotFoundException(error.message());
        }
        throw new ExternalApiException(error.message());
      }
      return objectMapper.readValue(response.body(), responseType);

    } catch (IOException | InterruptedException e) {
      throw new ExternalApiException("Error when " + method + " a recipe: " + e.getMessage(), e);
    }
  }
}
