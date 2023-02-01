package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.services.PreparationStepsService;
import com.abdulrehman1793.recipe.web.models.request.PreparationStepRequest;
import com.abdulrehman1793.recipe.web.models.response.PreparationStepResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/steps")
public class PreparationStepsController {
    private final PreparationStepsService preparationStepsService;

    @GetMapping("/{recipeId}")
    public List<PreparationStepResponse> findRecipeIngredients(@PathVariable UUID recipeId) {
        return preparationStepsService.findAllRecipePreparationSteps(recipeId);
    }

    @PostMapping("/{recipeId}")
    public PreparationStepResponse addIngredient(
            @PathVariable("recipeId") UUID id,
            @RequestBody @Valid PreparationStepRequest preparationStepRequest) {
        return preparationStepsService.addRecipePreparationStep(id, preparationStepRequest);
    }

    @PutMapping("/{id}")
    public PreparationStepResponse updateIngredient(
            @PathVariable("id") Long id,
            @RequestBody @Valid PreparationStepRequest preparationStepRequest) {
        return preparationStepsService.updateRecipePreparationStep(id, preparationStepRequest);
    }
}
