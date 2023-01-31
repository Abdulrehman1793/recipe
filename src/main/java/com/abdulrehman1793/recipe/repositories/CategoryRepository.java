package com.abdulrehman1793.recipe.repositories;

import com.abdulrehman1793.recipe.domains.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByTitleContainsOrDescriptionContains(String title, String description);

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);
}
