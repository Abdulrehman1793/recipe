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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/uom")
public class UnitOfMeasureController {
    private final String[] FIELDS = new String[]{"uom"};

    private final UnitOfMeasureService unitOfMeasureService;
    private final ControllerHelperService controllerHelperService;

    @GetMapping
    public ResponseEntity<Page<UnitOfMeasure>> findPage(
            @RequestParam(value = "page", defaultValue = AppConstant.PAGE, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstant.SIZE, required = false) int size,
            @RequestParam(value = "sort", defaultValue = "", required = false) String[] sorts) {

        Pageable pageable = PageRequest.of(page, size, controllerHelperService.sortRequestParameterToSort(FIELDS, sorts));

        return ResponseEntity.ok(unitOfMeasureService.findPage(pageable));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody String uom) {
        return ResponseEntity.ok(unitOfMeasureService.create(uom));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        unitOfMeasureService.delete(id);
    }
}
