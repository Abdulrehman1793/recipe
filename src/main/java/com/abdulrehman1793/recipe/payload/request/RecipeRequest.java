package com.abdulrehman1793.recipe.payload.request;

import com.abdulrehman1793.recipe.enums.Difficulty;
import lombok.Data;

@Data
public class RecipeRequest {
    private String title;
    private String subTitle;
    private String description;

    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;

    private Difficulty difficulty;
}
