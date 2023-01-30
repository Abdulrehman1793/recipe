package com.abdulrehman1793.recipe.web.models.request;

import com.abdulrehman1793.recipe.enums.Difficulty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecipeRequest {
    @NotBlank(message = "Recipe title is required")
    private String title;
    @NotBlank(message = "Recipe sub title is required")
    private String subTitle;
    @NotBlank(message = "Recipe description is required")
    private String description;
    @Min(message = "Negative or empty value is not allowed", value = 0)
    @Max(message = "Maximum time to prepare for any dish is 10 days.", value = 14400)
    private Integer prepTime;
    @Min(message = "At least one minute is needed to cook any recipe", value = 1)
    @Max(message = "Maximum time to cook any dish is 10 days.", value = 14400)
    private Integer cookTime;
    @Min(message = "At least one person should be served", value = 1)
    @Max(message = "Maximum person to be served should not exceed 1000", value = 1000)
    private Integer servings;

    private Difficulty difficulty;
}
