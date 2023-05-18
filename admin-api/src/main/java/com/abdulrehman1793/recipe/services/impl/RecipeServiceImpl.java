package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.services.CategoryService;
import com.abdulrehman1793.recipe.services.RecipeService;
import com.abdulrehman1793.recipe.web.mappers.RecipeMapper;
import com.abdulrehman1793.recipe.web.models.request.RecipeRequest;
import com.abdulrehman1793.recipe.web.models.response.RecipeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final RecipeMapper recipeMapper;

    @Override
    public Page<RecipeResponse> findPage(Pageable pageable) {
        return recipeRepository.findAll(pageable).map(recipeMapper::recipeToRecipeResponse);
    }


    @Override
    public RecipeResponse findRecipeById(UUID id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::recipeToRecipeResponse)
                .orElseThrow(() -> new NoSuchElementFoundException("Recipe not found for " + id));
    }

    @Override
    public RecipeResponse createRecipe(RecipeRequest recipeRequest) {

        Recipe recipe = recipeMapper.recipeRequestToRecipe(recipeRequest);

        if (recipe.getTitle().isEmpty()) {
            System.out.println("Check duplicate title error");
        }

        addRecipeCategories(recipe, recipeRequest.getCategories());

        recipe = recipeRepository.save(recipe);

        return recipeMapper.recipeToRecipeResponse(recipe);
    }

    @Override
    public RecipeResponse updateRecipe(UUID id, RecipeRequest recipeRequest) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException("Recipe not found for " + id));

        recipe = recipeMapper.recipeRequestToRecipe(recipe, recipeRequest);

        // Check duplicate title error

        addRecipeCategories(recipe, recipeRequest.getCategories());

        recipe = recipeRepository.save(recipe);

        return recipeMapper.recipeToRecipeResponse(recipe);
    }

    private void addRecipeCategories(Recipe recipe, List<Long> categories) {
        if (categories != null && categories.size() > 0) {
            recipe.setCategories(
                    new HashSet<>(categoryService.findAllByIds(categories)));
        }
    }
}
