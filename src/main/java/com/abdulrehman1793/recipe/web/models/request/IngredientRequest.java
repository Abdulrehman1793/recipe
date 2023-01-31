package com.abdulrehman1793.recipe.web.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IngredientRequest {
    @NotBlank(message = "Ingredient description is required")
    private String description;
    private BigDecimal amount;
    @NotNull(message = "Unit of measure is required")
    private Long uomId;
}
