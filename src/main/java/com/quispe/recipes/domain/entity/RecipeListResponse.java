package com.quispe.recipes.domain.entity;

import java.util.List;

public record RecipeListResponse(
    List<Recipe> recipes,
    int total,
    int skip,
    int limit) {
}
