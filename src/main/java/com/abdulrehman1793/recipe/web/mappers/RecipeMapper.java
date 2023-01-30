package com.abdulrehman1793.recipe.web.mappers;

import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.web.models.request.RecipeRequest;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface RecipeMapper {
    Recipe recipeRequestToRecipe(RecipeRequest recipeRequest);
}
