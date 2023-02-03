package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.services.CategoryService;
import com.abdulrehman1793.recipe.web.models.request.CategoryRequest;
import com.abdulrehman1793.recipe.web.models.response.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    public static final String CATEGORY_PATH = "/api/v2/category";
    public static final String CATEGORY_PATH_ID = CATEGORY_PATH + "/{categoryId}";
    private final CategoryService categoryService;

    @GetMapping(CATEGORY_PATH)
    public ResponseEntity<List<CategoryResponse>> findAll(@Param("keyword") String keyword) {
        return ResponseEntity.ok(categoryService.findAll(keyword));
    }

    @PostMapping(CATEGORY_PATH)
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.create(categoryRequest));
    }

    @PutMapping(CATEGORY_PATH_ID)
    public ResponseEntity<CategoryResponse> update(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.update(categoryId, categoryRequest));
    }

    @DeleteMapping(CATEGORY_PATH_ID)
    public void delete(@PathVariable("categoryId") Long categoryId) {
        categoryService.delete(categoryId);
    }
}
