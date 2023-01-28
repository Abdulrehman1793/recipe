package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.payload.request.RecipeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {
    Page<Recipe> findPage(Pageable pageable);

    Recipe createRecipe(RecipeRequest recipeRequest);
}
