package com.abdulrehman1793.recipe.web.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {
    private UUID id;
    private String title;
    private String subTitle;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private Set<CategoryResponse> categories;
}
