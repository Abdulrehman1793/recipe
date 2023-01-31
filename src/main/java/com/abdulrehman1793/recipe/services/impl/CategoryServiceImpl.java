package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.Category;
import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.CategoryRepository;
import com.abdulrehman1793.recipe.services.CategoryService;
import com.abdulrehman1793.recipe.web.mappers.CategoryMapper;
import com.abdulrehman1793.recipe.web.models.request.CategoryRequest;
import com.abdulrehman1793.recipe.web.models.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse findById(Long id) {
        return categoryMapper.categoryToCategoryResponse(findByIdOrThrow(id));
    }

    @Override
    public List<CategoryResponse> findAll(String keyword) {

        List<Category> categories;

        if (keyword == null || keyword.isBlank()) {
            categories = categoryRepository.findAll();
        } else {
            categories = categoryRepository.findByTitleContainsOrDescriptionContains(keyword, keyword);
        }

        return categories.stream()
                .map(categoryMapper::categoryToCategoryResponse).toList();
    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {

        if (categoryRepository.existsByTitle(categoryRequest.getTitle())) {
            throw new BadRequestException("Category already exists for " + categoryRequest.getTitle());
        }

        Category category = categoryMapper.categoryRequestToCategory(categoryRequest);

        return categoryMapper.categoryToCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {

        Category fetchedCategory = findByIdOrThrow(id);

        if (categoryRepository.existsByTitleAndIdNot(categoryRequest.getTitle(), id)) {
            throw new BadRequestException("Category already exists for " + categoryRequest.getTitle());
        }

        fetchedCategory.setTitle(categoryRequest.getTitle());
        fetchedCategory.setDescription(categoryRequest.getDescription());

        return categoryMapper.categoryToCategoryResponse(categoryRepository.save(fetchedCategory));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.delete(findByIdOrThrow(id));
    }

    private Category findByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Category not found for " + id));
    }
}
