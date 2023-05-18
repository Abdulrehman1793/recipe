package com.abdulrehman1793.recipe.web.mappers;

import com.abdulrehman1793.recipe.domains.Ingredient;
import com.abdulrehman1793.recipe.web.models.response.IngredientResponse;
import org.mapstruct.Mapper;

@Mapper
public interface IngredientMapper {
    IngredientResponse ingredientToIngredientResponse(Ingredient ingredient);
}
