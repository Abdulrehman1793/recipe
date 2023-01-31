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
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll(@Param("keyword") String keyword) {
        return ResponseEntity.ok(categoryService.findAll(keyword));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.create(categoryRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.update(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }
}
