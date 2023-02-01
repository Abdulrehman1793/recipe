package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.PreparationSteps;
import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.PreparationStepsRepository;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.services.PreparationStepsService;
import com.abdulrehman1793.recipe.web.mappers.PreparationStepMapper;
import com.abdulrehman1793.recipe.web.models.request.PreparationStepRequest;
import com.abdulrehman1793.recipe.web.models.response.PreparationStepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PreparationStepsServiceImpl implements PreparationStepsService {

    private final PreparationStepsRepository preparationStepsRepository;
    private final RecipeRepository recipeRepository;
    private final PreparationStepMapper preparationStepMapper;

    @Override
    public List<PreparationStepResponse> findAllRecipePreparationSteps(UUID recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementFoundException("Recipe not found for " + recipeId));

        List<PreparationSteps> preparationSteps = preparationStepsRepository.findAllByRecipe(recipe);

        return preparationSteps.stream()
                .map(preparationStepMapper::preparationStepToPreparationStepResponse).toList();
    }

    @Override
    public PreparationStepResponse addRecipePreparationStep(UUID recipeId, PreparationStepRequest preparationStepRequest) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementFoundException("Recipe not found for " + recipeId));

        PreparationSteps preparationSteps = PreparationSteps.builder()
                .description(preparationStepRequest.getDescription())
                .title(preparationStepRequest.getTitle())
                .priority(recipe.getPreparationSteps().size())
                .recipe(recipe)
                .build();

        preparationSteps = preparationStepsRepository.save(preparationSteps);

        return preparationStepMapper.preparationStepToPreparationStepResponse(preparationSteps);
    }

    @Override
    public PreparationStepResponse updateRecipePreparationStep(Long id, PreparationStepRequest preparationStepRequest) {
        PreparationSteps preparationSteps = preparationStepsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Preparation step not found for " + id));

        preparationSteps.setDescription(preparationStepRequest.getDescription());
        preparationSteps.setTitle(preparationStepRequest.getTitle());

        preparationSteps = preparationStepsRepository.save(preparationSteps);

        return preparationStepMapper.preparationStepToPreparationStepResponse(preparationSteps);
    }
}
