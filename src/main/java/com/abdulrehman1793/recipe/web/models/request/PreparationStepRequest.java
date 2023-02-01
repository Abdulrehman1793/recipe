package com.abdulrehman1793.recipe.web.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PreparationStepRequest {
    private int priority;
    @NotBlank(message = "Preparation Step title is required")
    private String title;

    private String description;
}
