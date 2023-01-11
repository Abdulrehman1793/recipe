package com.abdulrehman1793.recipe.repositories;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends JpaRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByUom(String uom);
}
