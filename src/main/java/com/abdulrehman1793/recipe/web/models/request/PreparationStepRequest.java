package com.abdulrehman1793.recipe.web.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreparationStepRequest {
    private int priority;
    @NotBlank(message = "Preparation Step title is required")
    private String title;

    private String description;
}
