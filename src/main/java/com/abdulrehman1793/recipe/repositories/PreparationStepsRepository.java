package com.abdulrehman1793.recipe.repositories;

import com.abdulrehman1793.recipe.domains.PreparationSteps;
import com.abdulrehman1793.recipe.domains.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreparationStepsRepository extends JpaRepository<PreparationSteps, Long> {
    List<PreparationSteps> findAllByRecipe(Recipe recipe);
}
