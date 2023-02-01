package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.web.models.request.RecipeRequest;
import com.abdulrehman1793.recipe.web.models.response.RecipeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RecipeService {
    Page<RecipeResponse> findPage(Pageable pageable);

    RecipeResponse findRecipeById(UUID id);

    RecipeResponse createRecipe(RecipeRequest recipeRequest);
}
