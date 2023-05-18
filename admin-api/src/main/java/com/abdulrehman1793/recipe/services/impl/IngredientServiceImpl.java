package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.Ingredient;
import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.IngredientRepository;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.repositories.UnitOfMeasureRepository;
import com.abdulrehman1793.recipe.services.IngredientService;
import com.abdulrehman1793.recipe.web.mappers.IngredientMapper;
import com.abdulrehman1793.recipe.web.models.request.IngredientRequest;
import com.abdulrehman1793.recipe.web.models.response.IngredientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientMapper ingredientMapper;


    @Override
    public List<IngredientResponse> findAllRecipeIngredient(UUID recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementFoundException("Recipe not found for " + recipeId));

        List<Ingredient> ingredients = ingredientRepository.findAllByRecipe(recipe);

        return ingredients.stream()
                .map(ingredientMapper::ingredientToIngredientResponse).toList();
    }

    @Override
    public IngredientResponse addRecipeIngredient(UUID recipeId, IngredientRequest ingredientRequest) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementFoundException("Recipe not found for " + recipeId));

        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(ingredientRequest.getUomId())
                .orElseThrow(() -> new NoSuchElementFoundException("Unit of measures not found for " + ingredientRequest.getUomId()));

        Ingredient ingredient = Ingredient.builder()
                .amount(ingredientRequest.getAmount())
                .description(ingredientRequest.getDescription())
                .unitOfMeasure(unitOfMeasure)
                .recipe(recipe)
                .build();

        ingredient = ingredientRepository.save(ingredient);

        return ingredientMapper.ingredientToIngredientResponse(ingredient);
    }

    @Override
    public IngredientResponse updateRecipeIngredient(Long id, IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(("Recipe ingredient not found for " + id)));

        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(ingredientRequest.getUomId())
                .orElseThrow(() -> new NoSuchElementFoundException("Unit of measures not found for " + ingredientRequest.getUomId()));

        ingredient.setDescription(ingredientRequest.getDescription());
        ingredient.setAmount(ingredientRequest.getAmount());
        ingredient.setUnitOfMeasure(unitOfMeasure);

        ingredient = ingredientRepository.save(ingredient);

        return ingredientMapper.ingredientToIngredientResponse(ingredient);
    }
}
