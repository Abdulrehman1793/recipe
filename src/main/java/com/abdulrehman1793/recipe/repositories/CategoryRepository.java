package com.abdulrehman1793.recipe.repositories;

import com.abdulrehman1793.recipe.domains.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
