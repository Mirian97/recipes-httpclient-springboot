package com.quispe.recipes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quispe.recipes.domain.dto.UpdateRecipeDTO;
import com.quispe.recipes.domain.entity.Recipe;
import com.quispe.recipes.service.RecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

  @Autowired
  private RecipeService service;

  @GetMapping
  public ResponseEntity<List<Recipe>> getAll() {
    return ResponseEntity.ok().body(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Recipe> getById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.getById(id));
  }

  @GetMapping("/search")
  public ResponseEntity<List<Recipe>> searchByName(@RequestParam String name) {
    return ResponseEntity.ok().body(service.searchByName(name));
  }

  @PostMapping
  public ResponseEntity<Recipe> getById(@RequestBody Recipe body) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(body));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Recipe> delete(@PathVariable Long id, @RequestBody UpdateRecipeDTO body) {
    return ResponseEntity.ok().body(service.update(id, body));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Recipe> delete(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.delete(id));
  }
}
