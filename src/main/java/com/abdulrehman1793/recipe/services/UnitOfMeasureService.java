package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnitOfMeasureService {
    UnitOfMeasure findById(Long id);

    Page<UnitOfMeasure> findPage(Pageable pageable);

    UnitOfMeasure create(String uom);

    void delete(Long id);
}
