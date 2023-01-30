package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import com.abdulrehman1793.recipe.payload.request.RecipeRequest;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    @Override
    public Page<Recipe> findPage(Pageable pageable) {

        Long test = 1L;
        for (int i = 0; i < 99999; i++) {
            test += i;
            for (int j = 0; j < 999; j++) {
                test += j;
            }
        }
//        if (test > 1000)
//            throw new BadRequestException("Custom error");

        System.out.println(pageable.getSort().toList());

        return recipeRepository.findAll(pageable);
    }

    @Override
    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new BadRequestException("Recipe not found for " + id));
    }

    @Override
    public Recipe createRecipe(RecipeRequest recipeRequest) {

        Recipe recipe = Recipe.builder()
                .title(recipeRequest.getTitle())
                .description(recipeRequest.getDescription())
                .difficulty(recipeRequest.getDifficulty())
                .cookTime(recipeRequest.getCookTime())
                .build();

        return recipeRepository.save(recipe);
    }
}
