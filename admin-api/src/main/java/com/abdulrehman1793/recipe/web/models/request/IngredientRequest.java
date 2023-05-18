package com.abdulrehman1793.recipe.web.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IngredientRequest {
    @Builder
    public IngredientRequest(String description, BigDecimal amount, Long uomId) {
        this.description = description;
        this.amount = amount;
        this.uomId = uomId;
    }

    @NotBlank(message = "Ingredient description is required")
    private String description;
    private BigDecimal amount;
    @NotNull(message = "Unit of measure is required")
    private Long uomId;
}
