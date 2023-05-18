package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;

import java.util.List;

public interface UnitOfMeasureService {
    UnitOfMeasureResponse findById(Long id);

    List<UnitOfMeasureResponse> findAll(String keyword);

    UnitOfMeasureResponse create(String uom);

    UnitOfMeasureResponse update(Long id, String uom);

    void delete(Long id);
}
