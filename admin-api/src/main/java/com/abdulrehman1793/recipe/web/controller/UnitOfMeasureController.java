package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.services.UnitOfMeasureService;
import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UnitOfMeasureController {

    public static final String UOM_PATH = "/api/v2/uom/";
    public static final String UOM_PATH_ID = UOM_PATH + "{uomId}";

    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping(UOM_PATH)
    public List<UnitOfMeasureResponse> findAll(@Param("keyword") String keyword) {
        return unitOfMeasureService.findAll(keyword);
    }

    @PostMapping(UOM_PATH)
    public ResponseEntity<UnitOfMeasureResponse> create(@RequestBody @Valid UnitOfMeasure uom) {
        return ResponseEntity.ok(unitOfMeasureService.create(uom.getUom()));
    }

    @PutMapping(UOM_PATH_ID)
    public ResponseEntity<UnitOfMeasureResponse> update(@PathVariable("uomId") Long uomId, @RequestBody @Valid UnitOfMeasure uom) {
        return ResponseEntity.ok(unitOfMeasureService.update(uomId, uom.getUom()));
    }

    @DeleteMapping(UOM_PATH_ID)
    public void delete(@PathVariable("uomId") Long uomId) {
        unitOfMeasureService.delete(uomId);
    }
}
