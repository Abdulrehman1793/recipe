package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.services.UnitOfMeasureService;
import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/uom")
public class UnitOfMeasureController {
    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping
    public ResponseEntity<List<UnitOfMeasureResponse>> findAll(@Param("uom") String uom) {
        return ResponseEntity.ok(unitOfMeasureService.findAll(uom));
    }

    @PostMapping
    public ResponseEntity<UnitOfMeasureResponse> create(@RequestBody UnitOfMeasure uom) {
        return ResponseEntity.ok(unitOfMeasureService.create(uom.getUom()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitOfMeasureResponse> update(@PathVariable Long id, @RequestBody UnitOfMeasure uom) {
        return ResponseEntity.ok(unitOfMeasureService.update(id, uom.getUom()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        unitOfMeasureService.delete(id);
    }
}
