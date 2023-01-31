package com.abdulrehman1793.recipe.repositories;

import com.abdulrehman1793.recipe.domains.Ingredient;
import com.abdulrehman1793.recipe.domains.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByRecipe(Recipe recipe);
}
