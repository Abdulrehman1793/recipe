package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.services.RecipeService;
import com.abdulrehman1793.recipe.web.mappers.RecipeMapper;
import com.abdulrehman1793.recipe.web.models.request.RecipeRequest;
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
    public Page<Recipe> findPage(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    @Override
    public Recipe findRecipeById(UUID id) {
        return recipeRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Recipe not found for " + id));
    }

    @Override
    public Recipe createRecipe(RecipeRequest recipeRequest) {

        Recipe recipe = recipeMapper.recipeRequestToRecipe(recipeRequest);

        if (recipe.getTitle().isEmpty()) {
            System.out.println("Check duplicate title error");
        }

        return recipeRepository.save(recipe);
    }
}
