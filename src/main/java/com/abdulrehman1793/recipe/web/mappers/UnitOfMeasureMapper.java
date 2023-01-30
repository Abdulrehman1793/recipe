package com.abdulrehman1793.recipe.web.mappers;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UnitOfMeasureMapper {
    UnitOfMeasureResponse unitOfMeasureToUnitOfMeasureResponse(UnitOfMeasure unitOfMeasure);
}
