package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;

public interface UnitOfMeasureService {
    UnitOfMeasure create(String uom);

    void delete(Long id);
}
