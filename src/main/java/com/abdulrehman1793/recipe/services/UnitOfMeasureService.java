package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;

import java.util.List;

public interface UnitOfMeasureService {
    UnitOfMeasure findById(Long id);

    List<UnitOfMeasureResponse> findAll(String uom);

    UnitOfMeasure create(String uom);

    UnitOfMeasure update(Long id, String uom);

    void delete(Long id);
}
