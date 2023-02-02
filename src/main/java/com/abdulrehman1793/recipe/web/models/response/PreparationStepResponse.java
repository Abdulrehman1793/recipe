package com.abdulrehman1793.recipe.web.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreparationStepResponse {
    private Long id;
    private int priority;
    private String title;

    private String description;
}
