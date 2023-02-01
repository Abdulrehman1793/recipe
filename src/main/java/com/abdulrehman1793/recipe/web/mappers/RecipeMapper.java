package com.abdulrehman1793.recipe.web.mappers;

import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.web.models.request.RecipeRequest;
import com.abdulrehman1793.recipe.web.models.response.RecipeResponse;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface RecipeMapper {
    Recipe recipeRequestToRecipe(RecipeRequest recipeRequest);

    RecipeResponse recipeToRecipeResponse(Recipe recipe);
}
