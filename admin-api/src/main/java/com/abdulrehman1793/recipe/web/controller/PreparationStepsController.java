package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.domains.PreparationSteps;
import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import com.abdulrehman1793.recipe.services.PreparationStepsService;
import com.abdulrehman1793.recipe.web.models.request.PreparationStepRequest;
import com.abdulrehman1793.recipe.web.models.response.PreparationStepResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PreparationStepsController {

    public static final String PREP_STEP_PATH = "/api/v2/steps";
    public static final String PREP_STEP_PATH_ID = PREP_STEP_PATH + "/{id}";
    private final PreparationStepsService preparationStepsService;

    @GetMapping(PREP_STEP_PATH + "/{recipeId}")
    public List<PreparationStepResponse> findRecipeIngredients(@PathVariable UUID recipeId) {
        return preparationStepsService.findAllRecipePreparationSteps(recipeId);
    }

    @PostMapping(PREP_STEP_PATH + "/{recipeId}")
    public PreparationStepResponse addIngredient(
            @PathVariable("recipeId") UUID id,
            @RequestBody @Valid PreparationStepRequest preparationStepRequest) {
        return preparationStepsService.addRecipePreparationStep(id, preparationStepRequest);
    }

    @PutMapping(PREP_STEP_PATH_ID)
    public PreparationStepResponse updateIngredient(
            @PathVariable("id") Long id,
            @RequestBody @Valid PreparationStepRequest preparationStepRequest) {
        return preparationStepsService.updateRecipePreparationStep(id, preparationStepRequest);
    }

    @PostMapping(PREP_STEP_PATH + "/images/{recipeId}")
    public PreparationStepResponse addIngredientWithImage(
            @PathVariable("recipeId") UUID id,
            @ModelAttribute @Valid PreparationStepRequest preparationStepRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new BadRequestException("Invalid preparation step request");

        MultipartFile imageFile = preparationStepRequest.getMultipartFile();


        return preparationStepsService.addRecipePreparationStep(id, preparationStepRequest);
    }
}
