package com.abdulrehman1793.recipe.payload;

import java.util.UUID;

public interface RecipeSimple {
    UUID getId();

    String getTitle();

    String getSubTitle();

    String getDescription();

    Integer getPrepTime();

    Integer getCookTime();

    Integer getServings();
}
