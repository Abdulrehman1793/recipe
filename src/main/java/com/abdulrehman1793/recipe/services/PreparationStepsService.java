package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.web.models.request.PreparationStepRequest;
import com.abdulrehman1793.recipe.web.models.response.PreparationStepResponse;

import java.util.List;
import java.util.UUID;

public interface PreparationStepsService {
    public List<PreparationStepResponse> findAllRecipePreparationSteps(UUID recipeId);

    public PreparationStepResponse addRecipePreparationStep(UUID recipeId, PreparationStepRequest preparationStepRequest);

    public PreparationStepResponse updateRecipePreparationStep(Long id, PreparationStepRequest preparationStepRequest);
}
