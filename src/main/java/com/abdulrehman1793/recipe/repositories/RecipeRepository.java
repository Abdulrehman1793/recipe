package com.abdulrehman1793.recipe.repositories;

import com.abdulrehman1793.recipe.domains.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}
