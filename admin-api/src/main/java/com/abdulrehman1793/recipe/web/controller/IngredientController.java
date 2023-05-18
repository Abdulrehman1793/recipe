package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.services.IngredientService;
import com.abdulrehman1793.recipe.web.models.request.IngredientRequest;
import com.abdulrehman1793.recipe.web.models.response.IngredientResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class IngredientController {

    public static final String INGREDIENT_PATH = "/api/v2/ingredient";
    public static final String INGREDIENT_PATH_ID = INGREDIENT_PATH + "/{id}";

    private final IngredientService ingredientService;

    @GetMapping(INGREDIENT_PATH + "/{recipeId}")
    public List<IngredientResponse> findRecipeIngredients(@PathVariable UUID recipeId) {
        return ingredientService.findAllRecipeIngredient(recipeId);
    }

    @PostMapping(INGREDIENT_PATH + "/{recipeId}")
    public IngredientResponse addIngredient(
            @PathVariable("recipeId") UUID id,
            @RequestBody @Valid IngredientRequest ingredientRequest) {
        return ingredientService.addRecipeIngredient(id, ingredientRequest);
    }

    @PutMapping(INGREDIENT_PATH_ID)
    public IngredientResponse updateIngredient(
            @PathVariable("id") Long id,
            @RequestBody @Valid IngredientRequest ingredientRequest) {
        return ingredientService.updateRecipeIngredient(id, ingredientRequest);
    }
}
