package com.abdulrehman1793.recipe.web.mappers;

import com.abdulrehman1793.recipe.domains.Category;
import com.abdulrehman1793.recipe.web.models.request.CategoryRequest;
import com.abdulrehman1793.recipe.web.models.response.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryResponse categoryToCategoryResponse(Category category);

    Category categoryRequestToCategory(CategoryRequest categoryRequest);
}
