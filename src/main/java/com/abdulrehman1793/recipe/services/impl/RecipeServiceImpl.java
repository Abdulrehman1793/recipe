package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.services.RecipeService;
import com.abdulrehman1793.recipe.web.mappers.RecipeMapper;
import com.abdulrehman1793.recipe.web.models.request.RecipeRequest;
import com.abdulrehman1793.recipe.web.models.response.RecipeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public Page<RecipeResponse> findPage(Pageable pageable) {
        return recipeRepository.findAll(pageable).map(recipeMapper::recipeToRecipeResponse);
    }


    @Override
    public RecipeResponse findRecipeById(UUID id) {
        return recipeRepository.findById(id).map(recipeMapper::recipeToRecipeResponse)
                .orElseThrow(() -> new NoSuchElementFoundException("Recipe not found for " + id));
    }

    @Override
    public RecipeResponse createRecipe(RecipeRequest recipeRequest) {

        Recipe recipe = recipeMapper.recipeRequestToRecipe(recipeRequest);

        if (recipe.getTitle().isEmpty()) {
            System.out.println("Check duplicate title error");
        }

        recipe = recipeRepository.save(recipe);

        return recipeMapper.recipeToRecipeResponse(recipe);
    }
}
