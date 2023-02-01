package com.abdulrehman1793.recipe.web.mappers;

import com.abdulrehman1793.recipe.domains.PreparationSteps;
import com.abdulrehman1793.recipe.web.models.response.PreparationStepResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PreparationStepMapper {
    PreparationStepResponse preparationStepToPreparationStepResponse(PreparationSteps preparationSteps);
}
