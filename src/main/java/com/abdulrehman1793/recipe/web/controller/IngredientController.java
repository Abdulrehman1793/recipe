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
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping("/{recipeId}")
    public List<IngredientResponse> findRecipeIngredients(@PathVariable UUID recipeId) {
        return ingredientService.findAllRecipeIngredient(recipeId);
    }

    @PostMapping("/{recipeId}")
    public IngredientResponse addIngredient(
            @PathVariable("recipeId") UUID id,
            @RequestBody @Valid IngredientRequest ingredientRequest) {
        return ingredientService.addRecipeIngredient(id, ingredientRequest);
    }

    @PutMapping("/{id}")
    public IngredientResponse updateIngredient(
            @PathVariable("id") Long id,
            @RequestBody @Valid IngredientRequest ingredientRequest) {
        return ingredientService.updateRecipeIngredient(id, ingredientRequest);
    }
}
