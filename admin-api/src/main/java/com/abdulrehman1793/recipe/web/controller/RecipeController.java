package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.services.RecipeService;
import com.abdulrehman1793.recipe.util.AppConstant;
import com.abdulrehman1793.recipe.util.ControllerHelperService;
import com.abdulrehman1793.recipe.web.models.request.RecipeRequest;
import com.abdulrehman1793.recipe.web.models.response.RecipeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final String[] FIELDS = new String[]{"title", "description", "difficulty"};

    private final RecipeService recipeService;
    private final ControllerHelperService controllerHelperService;

    @GetMapping
    public ResponseEntity<Page<RecipeResponse>> findPage(
            @RequestParam(value = "page", defaultValue = AppConstant.PAGE, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstant.SIZE, required = false) int size,
            @RequestParam(value = "sort", defaultValue = "", required = false) String[] sorts) {

        log.info("Request findPage: Page" + page + ", size:" + size + "Sort :" + Arrays.stream(sorts).toList());

        Pageable pageable = PageRequest.of(page, size, controllerHelperService.sortRequestParameterToSort(FIELDS, sorts));

        return ResponseEntity.ok(recipeService.findPage(pageable));
    }

    @GetMapping("/load")
    public ResponseEntity<Page<RecipeResponse>> loadPage(
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults(@SortDefault(sort = "id", direction = Sort.Direction.DESC)) Pageable pageable) {
        return ResponseEntity.ok(recipeService.findPage(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipeResponse> findRecipeById(@PathVariable UUID id) {
        return ResponseEntity.ok(recipeService.findRecipeById(id));
    }


    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody @Valid RecipeRequest recipeRequest) {
        return ResponseEntity.ok(recipeService.createRecipe(recipeRequest));
    }

    @PutMapping("/{recipe-id}")
    public ResponseEntity<RecipeResponse> updateRecipe(
            @RequestBody @Valid RecipeRequest recipeRequest, @PathVariable("recipe-id") UUID recipeId) {
        return ResponseEntity.ok(recipeService.updateRecipe(recipeId, recipeRequest));
    }
}
