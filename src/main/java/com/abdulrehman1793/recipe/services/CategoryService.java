package com.abdulrehman1793.recipe.services;

import com.abdulrehman1793.recipe.domains.Category;
import com.abdulrehman1793.recipe.web.models.request.CategoryRequest;
import com.abdulrehman1793.recipe.web.models.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll(String keyword);

    List<Category> findAllByIds(List<Long> ids);

    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse update(Long id, CategoryRequest categoryRequest);

    void delete(Long id);
}
