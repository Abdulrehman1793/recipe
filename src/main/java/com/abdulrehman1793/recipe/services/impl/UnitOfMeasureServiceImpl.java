package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.UnitOfMeasureRepository;
import com.abdulrehman1793.recipe.services.UnitOfMeasureService;
import com.abdulrehman1793.recipe.web.mappers.UnitOfMeasureMapper;
import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureMapper unitOfMeasureMapper;

    @Override
    public UnitOfMeasure findById(Long id) {
        return findByIdOrElseThrow(id);
    }

    @Override
    public List<UnitOfMeasureResponse> findAll(String uom) {

        List<UnitOfMeasure> unitOfMeasures;

        if (uom == null || uom.isBlank())
            unitOfMeasures = unitOfMeasureRepository.findAll();
        else
            unitOfMeasures = unitOfMeasureRepository.findAllByUomContaining(uom);

        return unitOfMeasures
                .stream()
                .map(unitOfMeasureMapper::unitOfMeasureToUnitOfMeasureResponse)
                .toList();
    }

    @Override
    public UnitOfMeasure create(String uom) {
        unitOfMeasureRepository.findByUom(uom).ifPresent(unitOfMeasure -> {
            throw new BadRequestException("Unit of measures already exists.");
        });

        return unitOfMeasureRepository.save(new UnitOfMeasure(uom));
    }

    @Override
    public UnitOfMeasure update(Long id, String uomText) {
        UnitOfMeasure uom = findByIdOrElseThrow(id);

        if (uomText.equalsIgnoreCase(uom.getUom())) {
            throw new BadRequestException("No changes detected");
        }

        unitOfMeasureRepository.findByUomAndIdNot(uomText, id).ifPresent(unitOfMeasure -> {
            throw new BadRequestException("Unit of measures already exists.");
        });

        uom.setUom(uomText);

        return unitOfMeasureRepository.save(uom);
    }

    @Override
    public void delete(Long id) {
        UnitOfMeasure uom = findByIdOrElseThrow(id);

        unitOfMeasureRepository.delete(uom);
    }

    private UnitOfMeasure findByIdOrElseThrow(Long id) {
        return unitOfMeasureRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Unit of measures not found for " + id));
    }
}
