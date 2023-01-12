package com.abdulrehman1793.recipe.controllers;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.services.UnitOfMeasureService;
import com.abdulrehman1793.recipe.util.AppConstant;
import com.abdulrehman1793.recipe.util.ControllerHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/uom")
public class UnitOfMeasureController {
    private final UnitOfMeasureService unitOfMeasureService;
    private final ControllerHelperService<UnitOfMeasure> controllerHelperService;

    @GetMapping
    public ResponseEntity<Page<UnitOfMeasure>> findPage(
            @RequestParam(value = "page", defaultValue = AppConstant.PAGE, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstant.SIZE, required = false) int size,
            @RequestParam(value = "sort", required = false) String[] sorts) {

        Pageable pageable = PageRequest.of(page, size,
                controllerHelperService.sortRequestParameterToSort(new UnitOfMeasure(), sorts));

        return ResponseEntity.ok(unitOfMeasureService.findPage(pageable));
    }
}
