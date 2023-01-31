package com.abdulrehman1793.recipe.web.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category title is required")
    private String title;
    private String description;
}
