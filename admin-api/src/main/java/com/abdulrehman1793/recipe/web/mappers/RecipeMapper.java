package com.abdulrehman1793.recipe.web.mappers;

import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.web.models.request.RecipeRequest;
import com.abdulrehman1793.recipe.web.models.response.RecipeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = DateMapper.class)
public interface RecipeMapper {
    @Mapping(target = "categories", ignore = true)
    Recipe recipeRequestToRecipe(@MappingTarget Recipe recipe, RecipeRequest recipeRequest);

    @Mapping(target = "categories", ignore = true)
    Recipe recipeRequestToRecipe(RecipeRequest recipeRequest);

    RecipeResponse recipeToRecipeResponse(Recipe recipe);
}
