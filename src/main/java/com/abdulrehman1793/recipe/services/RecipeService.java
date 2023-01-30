package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.web.models.request.RecipeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RecipeService {
    Page<Recipe> findPage(Pageable pageable);

    Recipe findRecipeById(UUID id);

    Recipe createRecipe(RecipeRequest recipeRequest);
}
