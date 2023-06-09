package com.abdulrehman1793.recipe.web.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureResponse unitOfMeasure;
}
