package com.quispe.recipes.domain.entity;

import java.util.List;

public record Recipe(
    Long id,
    String name,
    List<String> ingredients,
    List<String> instructions,
    Integer prepTimeMinutes,
    Integer cookTimeMinutes,
    Integer servings,
    Difficulty difficulty,
    String cuisine,
    Integer caloriesPerServing,
    List<String> tags,
    Integer userId,
    String image,
    Integer rating,
    Integer reviewCount,
    List<String> mealType) {
}
