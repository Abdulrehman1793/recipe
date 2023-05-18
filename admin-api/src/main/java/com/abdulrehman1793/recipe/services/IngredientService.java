package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.web.models.request.IngredientRequest;
import com.abdulrehman1793.recipe.web.models.response.IngredientResponse;

import java.util.List;
import java.util.UUID;

public interface IngredientService {
    public List<IngredientResponse> findAllRecipeIngredient(UUID recipeId);

    public IngredientResponse addRecipeIngredient(UUID recipeId, IngredientRequest ingredientRequest);

    public IngredientResponse updateRecipeIngredient(Long id, IngredientRequest ingredientRequest);
}
