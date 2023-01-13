package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import com.abdulrehman1793.recipe.repositories.UnitOfMeasureRepository;
import com.abdulrehman1793.recipe.services.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public UnitOfMeasure findById(Long id) {
        return findByIdOrElseThrow(id);
    }

    @Override
    public Page<UnitOfMeasure> findPage(Pageable pageable) {
        return unitOfMeasureRepository.findAll(pageable);
    }

    @Override
    public UnitOfMeasure create(String uom) {
        unitOfMeasureRepository.findByUom(uom).ifPresent(unitOfMeasure -> {
            throw new BadRequestException("Unit of measures already exists.");
        });

        return unitOfMeasureRepository.save(new UnitOfMeasure(uom));
    }

    @Override
    public void delete(Long id) {
        UnitOfMeasure uom = findByIdOrElseThrow(id);

        unitOfMeasureRepository.delete(uom);
    }

    private UnitOfMeasure findByIdOrElseThrow(Long id) {
        return unitOfMeasureRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Unit of measures not found for " + id));
    }
}
